package org.gojul.gojulutils.hibernateutils;

/**
 * Interface {@code GojulHibernateMergeable} is used in order to easily merge
 * Hibernate entities. This is useful notably for objects stored within Hibernate
 * collections because it avoids to know when to use {@code session.merge()} and
 * {@code session.saveOrUpdate}. Actually this is because within collections Hibernate
 * relies on object identity rather than the usual {@code equals()} and {@code hashCode}
 * operations.
 *  
 * @author julien
 *
 * @param <E> the class of elements to merge.
 * 
 * @see org.gojul.gojulutils.hibernateutils.GojulHibernateCollectionsMergeTool
 */
public interface GojulHibernateMergeable<E> {

	/**
	 * Merge the current entity with {@code entity}. Note implementors of this
	 * method should just ignore {@code null} values and not attempt to raise
	 * exceptions.
	 * 
	 * @param entity the entity to merge.
	 */
	public void mergeEntity(E entity);
	
}
