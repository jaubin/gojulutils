package org.gojul.gojulutils.data;

import java.util.*;

/**
 * <p>
 * Class {@code GojulJoinData} contains the data required in order to perform
 * an equivalent to the JOIN operations in database. Basically it works as such :
 * <ul>
 *     <li>There are two classes, S and T, on which the join must be performed.</li>
 *     <li>For each of the classes, a dedicated function exists whose purpose is to generate the JOIN key of type K.</li>
 *     <li>Then another service performs the JOIN itself, {@link GojulJoinDataService}. The JOIN is done
 *     for matching keys. Objects which have a {@code null} key on one side are bound to the objects with a {@code null}
 *     key on the other side.</li>
 * </ul>
 * </p>
 * <p>Note that this object is immutable if and only if S and T are immutable classes.</p>
 *
 * @param <K> the type of the JOIN key.
 * @param <S> the type of the first object to join.
 * @param <T> the type of the second object to join.
 *
 * @see org.gojul.gojulutils.data.GojulJoinDataService
 */
public class GojulJoinData<K, S, T> {

    /**
     * Class {@code GojulJoinDataKey} purpose is to compute
     * the JOIN key for an object of type {@code V}. Note that
     * if you assume your objects are nullable this key should
     * take the {@code null} case into account.
     *
     * @param <K> the type of the key.
     * @param <V> the type of the value for which the key is generated.
     */
    @FunctionalInterface
    public interface GojulJoinDataKey<K, V> {

        /**
         * Return the key corresponding to value {@code value}.
         * @param value the value for which the key must be computed.
         * @return the key corresponding to value {@code value}.
         */
        K getKey(final V value);
    }

    private final Map<K, List<S>> leftElementsPerKey;
    private final Map<K, List<T>> rightElementsKey;

    /**
     * Constructor.
     *
     * @param leftKey the function in charge of generating the key for the left elements to join.
     * @param leftElements the left elements to join.
     * @param rightKey the function in charge of generating the key for the right elements to join.
     * @param rightElements the right elements to join.
     *
     * @throws NullPointerException if any of the method parameters is {@code null}.
     */
    public GojulJoinData(final GojulJoinDataKey<K, S> leftKey, final Iterable<S> leftElements,
                         final GojulJoinDataKey<K, T> rightKey, final Iterable<T> rightElements) {
        Objects.requireNonNull(leftKey, "leftKey is null");
        Objects.requireNonNull(leftElements, "leftElements is null");
        Objects.requireNonNull(rightKey, "rightKey is null");
        Objects.requireNonNull(rightElements, "rightElements is null");

        this.leftElementsPerKey = buildElementsMapPerKey(leftKey, leftElements);
        this.rightElementsKey = buildElementsMapPerKey(rightKey, rightElements);
    }

    private <V> Map<K, List<V>> buildElementsMapPerKey(final GojulJoinDataKey<K, V> keyGen, final Iterable<V> data) {
        // Here we cannot simply use a groupingBy as groupingBy does not accept null keys.
        // Thus we use a LinkedHashMap to make it easier to write unit tests later on.
        Map<K, List<V>> result = new LinkedHashMap<>();

        for (V elem: data) {
            K key = keyGen.getKey(elem);
            List<V> valuesForKey = result.get(key);
            if (valuesForKey == null) {
                valuesForKey = new ArrayList<>();
                result.put(key, valuesForKey);
            }
            valuesForKey.add(elem);
        }

        return result;
    }

    /**
     * Return the left elements, stored per key. Note that this method is package
     * private because outer classes should not use it directly.
     *
     * @return the left elements stored per key.
     */
    Map<K, List<S>> getLeftElementsPerKey() {
        return deepUnmodifiableMap(leftElementsPerKey);
    }

    /**
     * Return the right elements, stored per key. Note that this method is package
     * private because outer classes should not use it directly.
     *
     * @return the right elements stored per key.
     */
    Map<K, List<T>> getRightElementsPerKey() {
        return deepUnmodifiableMap(rightElementsKey);
    }

    private <V> Map<K, List<V>> deepUnmodifiableMap(final Map<K, List<V>> map) {
        Map<K, List<V>> result = new LinkedHashMap<>();

        for (Map.Entry<K, List<V>> entry: map.entrySet()) {
            result.put(entry.getKey(), Collections.unmodifiableList(entry.getValue()));
        }

        return Collections.unmodifiableMap(result);
    }

    // Equals and HashCode are here only to make it easier to write unit tests.

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GojulJoinData<?, ?, ?> that = (GojulJoinData<?, ?, ?>) o;

        if (leftElementsPerKey != null ? !leftElementsPerKey.equals(that.leftElementsPerKey) : that.leftElementsPerKey != null)
            return false;
        return rightElementsKey != null ? rightElementsKey.equals(that.rightElementsKey) : that.rightElementsKey == null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = leftElementsPerKey != null ? leftElementsPerKey.hashCode() : 0;
        result = 31 * result + (rightElementsKey != null ? rightElementsKey.hashCode() : 0);
        return result;
    }
}
