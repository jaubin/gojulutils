package org.gojul.gojulutils.safetools;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class {@code GojulEncapsulationUtils} contains safe methods to help you
 * encapsulate your object instances by returning copies of objects instead
 * of objects themselves. The goal here is notably to allow you to remove
 * {@code null} checks prior to doing defensive copy.
 * 
 * @author julien
 *
 */
public class GojulEncapsulationUtils {

	/**
	 * Private constructor. Prevents class
	 * from being instanciated.
	 */
	private GojulEncapsulationUtils() {
		
	}
	
	/**
	 * Return an unmodifiable {@link List} around {@code l},
	 * or {@code null} if {@code l} is {@code null}.
	 * @param l the {@link List} to wrap.
	 * @return n unmodifiable {@link List} around {@code l},
	 * or {@code null} if {@code l} is {@code null}.
	 */
	public static <T> List<T> unmodifiableList(final List<T> l) {
		return l == null ? null: Collections.unmodifiableList(l);
	}
	
	/**
	 * Return an unmodifiable {@link Set} around {@code s},
	 * or {@code null} if {@code s} is {@code null}.
	 * @param s the {@link Set} to wrap.
	 * @return an unmodifiable {@link Set} around {@code s},
	 * or {@code null} if {@code s} is {@code null}.
	 */
	public static <T> Set<T> unmodifiableSet(final Set<T> s) {
		return s == null ? null: Collections.unmodifiableSet(s);
	}
		
	/**
	 * Return an unmodifiable {@link Collection} around {@code c},
	 * or {@code null} if {@code c} is {@code null}.
	 * @param c the {@link Collection} to wrap.
	 * @return an unmodifiable {@link Collection} around {@code c},
	 * or {@code null} if {@code c} is {@code null}.
	 */
	public static <T> Collection<T> unmodifiableCollection(final Collection<T> c) {
		return c == null ? null: Collections.unmodifiableCollection(c);
	}
	
	/**
	 * Return an unmodifiable {@link Map} around {@code m},
	 * or a {@code null} if {@code m} is {@code null}.
	 * @param m the {@link Map} to wrap.
	 * @return an unmodifiable {@link Map} around {@code m},
	 * or a {@code null} if {@code m} is {@code null}.
	 */
	public static <K, V> Map<K, V> unmodifiableMap(final Map<K, V> m) {
		return m == null ? null: Collections.unmodifiableMap(m);
	}
	
	/**
	 * Return a copy of {@code d} if {@code d} is not {@code null},
	 * {@code null} otherwise.
	 * @param d the {@link Date} instance to copy.
	 * @return a copy of {@code d} if {@code d} is not {@code null},
	 * {@code null} otherwise.
	 */
	public static Date copyDate(final Date d) {
		return d == null ? null: new Date(d.getTime());
	}
	
	
}
