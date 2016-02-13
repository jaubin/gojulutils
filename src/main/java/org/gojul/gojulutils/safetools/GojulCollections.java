package org.gojul.gojulutils.safetools;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Class {@code GojulSafeCollections} contains wrappers for the most commonly used
 * methods of {@link java.util.Collections} class. Contrary to the Java standard API
 * these ones happily handle the {@code null} case and so on.
 * 
 * @author julien
 *
 */
public class GojulCollections {

	/**
	 * Private constructor. Prevents class
	 * from being instanciated.
	 */
	private GojulCollections() {
		
	}
	
	/**
	 * Return an unmodifiable {@link List} around {@code l},
	 * or an empty unmodifiable list if {@code l} is {@code empty}.
	 * @param l the {@link List} to wrap.
	 * @return an unmodifiable {@link List} around {@code l},
	 * or an empty unmodifiable list if {@code l} is {@code empty}.
	 */
	public static <T> List<T> unmodifiableList(final List<T> l) {
		return l == null ? Collections.<T> emptyList(): Collections.unmodifiableList(l);
	}
	
	/**
	 * Return an unmodifiable {@link Set} around {@code s},
	 * or an empty unmodifiable set if {@code s} is {@code empty}.
	 * @param s the {@link Set} to wrap.
	 * @return an unmodifiable {@link Set} around {@code s},
	 * or an empty unmodifiable set if {@code s} is {@code empty}.
	 */
	public static <T> Set<T> unmodifiableSet(final Set<T> s) {
		return s == null ? Collections.<T> emptySet(): Collections.unmodifiableSet(s);
	}
}
