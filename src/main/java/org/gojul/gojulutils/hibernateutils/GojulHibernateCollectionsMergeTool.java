package org.gojul.gojulutils.hibernateutils;

import org.gojul.gojulutils.validation.GojulPreconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * <p>
 * Class {@code GojulHibernateCollectionsMergeTool} is used to merge Hibernate collections.
 * Merging such collections is quite tricky because Hibernate relies on object references rather
 * than the usual {@code equals()} and {@code hashCode()} method to do so. This object solves the
 * issue by merging what can be merged, and adding or removing elements to add or delete from the
 * collection.
 * </p>
 * <p>
 * Beware that this object is not thread-safe.
 * </p>
 *
 * @param <K> the type of the key of elements. This should be the type of the element primary keys.
 * @param <E> the type of elements to merge.
 * @author julien
 * @see org.gojul.gojulutils.hibernateutils.GojulHibernateKeyInstanciator
 */
public class GojulHibernateCollectionsMergeTool<K, E extends GojulHibernateMergeable<E>> {

    private final static Logger log = LoggerFactory.getLogger(GojulHibernateCollectionsMergeTool.class);

    private GojulHibernateKeyInstanciator<K, E> keyInstanciator;

    private Map<K, List<E>> sourceEntitiesByKeys;

    /**
     * Constructor.
     *
     * @param keyInstanciator the key instanciator used to generate keys.
     * @param sourceEntities  the list of source entities. Actually they will serve as the merge
     *                        source, and will be merged with target entities. However the entities from this collection will
     *                        remain unaltered.
     * @throws NullPointerException     if any of the method parameters is {@code null}.
     * @throws IllegalArgumentException if {@code sourceEntities} is {@code null}.
     */
    public GojulHibernateCollectionsMergeTool(final GojulHibernateKeyInstanciator<K, E> keyInstanciator,
                                              final Set<E> sourceEntities) {
        Objects.requireNonNull(keyInstanciator, "keyInstanciator is null");
        Objects.requireNonNull(sourceEntities, "sourceEntities is null");
        GojulPreconditions.checkAssertion(!sourceEntities.contains(null), "sourceEntities contains null value while forbidden");
        this.keyInstanciator = keyInstanciator;
        this.sourceEntitiesByKeys = new HashMap<>();
        for (E entity : sourceEntities) {
            K key = keyInstanciator.generateKey(entity);
            List<E> entities = this.sourceEntitiesByKeys.get(key);
            if (entities == null) {
                entities = new ArrayList<>();
                this.sourceEntitiesByKeys.put(key, entities);
            }
            entities.add(entity);
        }
    }

    /**
     * <p>
     * Update the set {@code entities} with the entities stored in the current instance. This
     * method will update the elements of {@code entities} with the content of the ones
     * contained in the current object instance without replacing the objects themselves, and
     * add the elements contained in this instance which are not present in {@code entities}.
     * No element will be deleted from {@code entities}.
     * </p>
     * <p>
     * BEWARE : if you wish to add to {@code entities} elements already known from the database
     * you must ensure that these elements are already known from the current Hibernate session,
     * otherwise merge will fail with Hibernate complaining that some elements are already present
     * in database but not bound to a session.
     * </p>
     *
     * @param entities the set of entities to update.
     * @throws NullPointerException     if {@code entities} is {@code null}.
     * @throws IllegalArgumentException if {@code entities} contains the {@code null} value.
     * @throws IllegalStateException    if there are several entities from this object which match
     *                                  a single key provided by an element of {@code entities}, as this would prevent the merge
     *                                  from being done.
     */
    public void mergeEntitiesWithoutDelete(final Set<E> entities) {
        Objects.requireNonNull(entities, "entities is null");
        GojulPreconditions.checkAssertion(!entities.contains(null), "entities contains null value");

        Set<K> unprocessedKeys = new HashSet<>(sourceEntitiesByKeys.keySet());
        for (E entityToMerge : entities) {
            E sourceEntity = getSourceEntityAndMarkKeyAsProcessed(entityToMerge, unprocessedKeys);
            entityToMerge.mergeEntity(sourceEntity);
        }

        addRemainingEntities(entities, unprocessedKeys);
    }

    /**
     * <p>
     * Update the set {@code entities} with the entities stored in the current instance. This
     * method will update the elements of {@code entities} with the content of the ones
     * contained in the current object instance without replacing the objects themselves, and
     * add the elements contained in this instance which are not present in {@code entities}.
     * Elements contained in {@code entities} which are not present in the set of entities of the
     * current instance are removed from {@code entities}.
     * </p>
     * <p>
     * BEWARE : if you wish to add to {@code entities} elements already known from the database
     * you must ensure that these elements are already known from the current Hibernate session,
     * otherwise merge will fail with Hibernate complaining that some elements are already present
     * in database but not bound to a session.
     * </p>
     * <p>
     * This method returns the keys of the entities which have been removed from {@code entities}. This
     * is necessary because the only safe way to actually remove entities from a {@code @OneToMany} mapping
     * consists in both removing them from the collection, and then remove them by a DAO call before saving
     * the entity which contains the {@code OneToMany} relationship.
     * </p>
     *
     * @param entities the set of entities to update.
     * @return the set of keys of the removed elements from {@code entitiess}. Such a {@link Set} can then be
     * directly used in a database {@code delete} call.
     * @throws NullPointerException     if {@code entities} is {@code null}.
     * @throws IllegalArgumentException if {@code entities} contains the {@code null} value.
     * @throws IllegalStateException    if there are several entities from this object which match
     *                                  a single key provided by an element of {@code entities}, as this would prevent the merge
     *                                  from being done.
     */
    public Set<K> mergeEntitiesWithDelete(final Set<E> entities) {
        Objects.requireNonNull(entities, "entities is null");
        GojulPreconditions.checkAssertion(!entities.contains(null), "entities contains null value");

        Set<K> keysToRemove = new HashSet<>();
        Set<K> unprocessedKeys = new HashSet<>(sourceEntitiesByKeys.keySet());
        for (Iterator<E> it = entities.iterator(); it.hasNext(); ) {
            E entityToMerge = it.next();
            E sourceEntity = getSourceEntityAndMarkKeyAsProcessed(entityToMerge, unprocessedKeys);
            if (sourceEntity == null) {
                it.remove();
                keysToRemove.add(keyInstanciator.generateKey(entityToMerge));
            } else {
                entityToMerge.mergeEntity(sourceEntity);
            }
        }

        addRemainingEntities(entities, unprocessedKeys);

        return keysToRemove;
    }

    private E getSourceEntityAndMarkKeyAsProcessed(final E entityToMerge, final Set<K> unprocessedKeys) {
        K key = keyInstanciator.generateKey(entityToMerge);
        E result = getEntityForKey(key);
        unprocessedKeys.remove(key);
        return result;
    }

    private E getEntityForKey(final K key) {
        List<E> entities = sourceEntitiesByKeys.get(key);
        if (entities == null || entities.isEmpty()) {
            return null;
        }
        if (entities.size() > 1) {
            String msg = String.format("Multiple entities found for key %s - cannot merge", key);
            log.error(msg);
            throw new IllegalStateException(msg);
        }
        return entities.get(0);
    }

    private void addRemainingEntities(final Set<E> entities, final Set<K> unprocessedKeys) {
        for (K key : unprocessedKeys) {
            List<E> sourceEntities = sourceEntitiesByKeys.get(key);
            if (sourceEntities != null) {
                entities.addAll(sourceEntities);
            }
        }
    }

}
