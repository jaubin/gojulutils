package org.gojul.gojulutils.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class {@code GojulFilterComposite} is a composite implementation of
 * the {@link GojulFilter} interface. It simply aggregates other {@link GojulFilter}
 * instances so that you could write your own filtering rules much more easily.
 *
 * @param <S> the type of objects to accept.
 * @param <T> the type of the filtering context objects.
 */
public class GojulFilterComposite<S, T> implements GojulFilter<S, T> {

    private final List<GojulFilter<S, T>> filters;

    /**
     * Constructor.
     *
     * @param filters the list of filters to apply. These filters will be applied
     *                following the order in which they're declared in the list.
     * @throws NullPointerException if {@code filters} is {@code null}.
     */
    public GojulFilterComposite(final List<GojulFilter<S, T>> filters) {
        Objects.requireNonNull(filters, "filters is null");
        this.filters = new ArrayList<>(filters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean accept(final S value, final T context) {
        Objects.requireNonNull(value, "value is null");

        for (GojulFilter<S, T> filter : filters) {
            if (!filter.accept(value, context)) {
                return false;
            }
        }

        return true;
    }
}
