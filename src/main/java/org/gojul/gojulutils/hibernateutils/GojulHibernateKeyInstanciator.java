package org.gojul.gojulutils.hibernateutils;

/**
 * Interface {@code GojulHibernateKeyInstanciator} allows to generate the hibernate key
 * of an element to merge with the {@code GojulHibernateCollectionsMergeTool} class. The
 * generated key should typically be the database primary key, and for elements which are
 * still not stored in database implementors should simply return {@code null} values or
 * zero for scalar values.
 *
 * @param <K> the type of the returned key.
 * @param <E> the element type for which the key must be generated.
 * @author julien
 * @see org.gojul.gojulutils.hibernateutils.GojulHibernateCollectionsMergeTool
 */
public interface GojulHibernateKeyInstanciator<K, E extends GojulHibernateMergeable<E>> {

    /**
     * Generate the key corresponding to element {@code element}.
     *
     * @param element the element for which the key must be generated.
     * @return the key corresponding to element {@code element}.
     */
    K generateKey(final E element);

}
