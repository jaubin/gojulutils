package org.gojul.gojulutils.safetools;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Class {@code GojulEncapsulationUtils} contains safe methods to help you
 * encapsulate your object instances by returning copies of objects instead
 * of objects themselves. The goal here is notably to allow you to remove
 * {@code null} checks prior to doing defensive copy.
 *
 * @author julien
 */
public class GojulEncapsulationUtils {

    private GojulEncapsulationUtils() {
        // Private constructor. Prevents class from
        // being instanciated from the outside.
        throw new RuntimeException("Shoo away !!!");
    }

    /**
     * Return an unmodifiable {@link List} around {@code l},
     * or {@code null} if {@code l} is {@code null}.
     *
     * @param l   the {@link List} to wrap.
     * @param <T> the type of objects of the collection.
     * @return n unmodifiable {@link List} around {@code l},
     * or {@code null} if {@code l} is {@code null}.
     */
    public static <T> List<T> unmodifiableList(final List<T> l) {
        return l == null ? null : Collections.unmodifiableList(l);
    }

    /**
     * Return an unmodifiable {@link Set} around {@code s},
     * or {@code null} if {@code s} is {@code null}.
     *
     * @param s   the {@link Set} to wrap.
     * @param <T> the type of objects of the collection.
     * @return an unmodifiable {@link Set} around {@code s},
     * or {@code null} if {@code s} is {@code null}.
     */
    public static <T> Set<T> unmodifiableSet(final Set<T> s) {
        return s == null ? null : Collections.unmodifiableSet(s);
    }

    /**
     * Return an unmodifiable {@link Collection} around {@code c},
     * or {@code null} if {@code c} is {@code null}.
     *
     * @param c   the {@link Collection} to wrap.
     * @param <T> the type of objects of the collection.
     * @return an unmodifiable {@link Collection} around {@code c},
     * or {@code null} if {@code c} is {@code null}.
     */
    public static <T> Collection<T> unmodifiableCollection(final Collection<T> c) {
        return c == null ? null : Collections.unmodifiableCollection(c);
    }

    /**
     * Return an unmodifiable {@link Map} around {@code m},
     * or a {@code null} if {@code m} is {@code null}.
     *
     * @param m   the {@link Map} to wrap.
     * @param <K> the type of the Map keys.
     * @param <V> the type of the Map values.
     * @return an unmodifiable {@link Map} around {@code m},
     * or a {@code null} if {@code m} is {@code null}.
     */
    public static <K, V> Map<K, V> unmodifiableMap(final Map<K, V> m) {
        return m == null ? null : Collections.unmodifiableMap(m);
    }

    /**
     * Return a copy of {@code d} if {@code d} is not {@code null},
     * {@code null} otherwise.
     *
     * @param d the {@link Date} instance to copy.
     * @return a copy of {@code d} if {@code d} is not {@code null},
     * {@code null} otherwise.
     */
    public static Date copyDate(final Date d) {
        return d == null ? null : new Date(d.getTime());
    }

    /**
     * Copy the array {@code source} to a target array which contains the
     * same elements. This method just copies the array itself but does
     * not perform deep copy, so it is not safe with arrays of mutable
     * values. Note that if {@code source} is empty this method
     * returns {@code source} itself as in this case {@code source} is immutable.
     *
     * @param source the source objects to clone.
     * @param <T>    the type of elements of the array.
     * @return a copy of {@code source}.
     * @see GojulEncapsulationUtils#deepCopyArray(Object[], GojulCopyFunction)
     */
    public static <T> T[] copyArray(final T[] source) {
        if (source == null) {
            return null;
        }
        int len = source.length;
        // No risk here, an empty array is always immutable...
        if (len == 0) {
            return source;
        }
        @SuppressWarnings("unchecked")
        T[] copy = (T[]) Array.newInstance(source[0].getClass(), source.length);
        System.arraycopy(source, 0, copy, 0, len);
        return copy;
    }

    /**
     * Perform a deep copy of the array {@code source}, by copying not
     * only the array itself but also its values. While this method
     * is not as performant as {@link GojulEncapsulationUtils#copyArray(Object[])}
     * it is safe for mutable values.
     *
     * @param source       the source array to copy.
     * @param copyFunction the interface which defines how copies are instanciated.
     * @return a copy of {@code source} in which all the object instances are copies
     * of the values in {@code source}.
     * @throws NullPointerException if {@code copyFunction} is {@code null}.
     */
    public static <T> T[] deepCopyArray(final T[] source, final GojulCopyFunction<T> copyFunction) {
        Objects.requireNonNull(copyFunction, "copyFunction is null");
        if (source == null) {
            return null;
        }
        int len = source.length;
        // No risk here, an empty array is always immutable...
        if (len == 0) {
            return source;
        }
        @SuppressWarnings("unchecked")
        T[] copy = (T[]) Array.newInstance(source[0].getClass(), source.length);

        for (int i = 0; i < len; i++) {
            T elem = source[i];
            if (elem == null) {
                copy[i] = null;
            } else {
                copy[i] = copyFunction.copy(elem);
            }
        }

        return copy;
    }

    /**
     * Interface {@code GojulCopyFunction} is a functional interface
     * which purpose is to have a generic mechanism to perform deep copies.
     *
     * @param <T> the class of the element to copy.
     * @author julien
     */
    public static interface GojulCopyFunction<T> {

        /**
         * Perform a deep copy of the object {@code elem} and returns it.
         * Note that this method does not need to handle the {@code null} case.
         *
         * @param elem the element to copy.
         * @return a deep copy of the object {@code elem} and returns it.
         */
        public T copy(final T elem);
    }


}
