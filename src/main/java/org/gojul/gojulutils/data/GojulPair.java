package org.gojul.gojulutils.data;

/**
 * Class {@code GojulPair} is a simple stupid pair class. This class is notably necessary
 * when emulating JOIN in database and such a class does not exist natively in the JDK.
 * This object is immutable as long as the object it contains are immutable. Since
 * this object is not serializable it should not be stored in objects which could be serialized,
 * especially Java HttpSession objects.
 *
 * @param <S> the type of the first object of the pair.
 * @param <T> the type of the second object of the pair.
 * @author jaubin
 */
public final class GojulPair<S, T> {

    private final S first;
    private final T second;

    /**
     * Constructor. Both parameters are nullable. Note that this constructor
     * does not perform any defensive copy as it is not possible there.
     *
     * @param first  the first object.
     * @param second the second object.
     */
    public GojulPair(final S first, final T second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Return the first object.
     *
     * @return the first object.
     */
    public S getFirst() {
        return first;
    }

    /**
     * Return the second object.
     *
     * @return the second object.
     */
    public T getSecond() {
        return second;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GojulPair<?, ?> pair = (GojulPair<?, ?>) o;

        if (getFirst() != null ? !getFirst().equals(pair.getFirst()) : pair.getFirst() != null) return false;
        return getSecond() != null ? getSecond().equals(pair.getSecond()) : pair.getSecond() == null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = getFirst() != null ? getFirst().hashCode() : 0;
        result = 31 * result + (getSecond() != null ? getSecond().hashCode() : 0);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "GojulPair{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }
}
