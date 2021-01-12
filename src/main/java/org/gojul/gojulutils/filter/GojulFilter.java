package org.gojul.gojulutils.filter;

/**
 * Interface {@code GojulFilter} is the generic interface used in order to perform
 * filtering. The goal here is to make it easy for you to create simple filters that
 * can be assembled through a composite pattern. This way, each accept remains simple,
 * so that you could test it extensively. Thus using such an organization enables separation
 * of concerns, meaning that on one side you iterate over a list of elements to accept, and
 * on the other side you implement the accept logic.
 *
 * @param <S> the type of elements to accept.
 * @param <T> the type of the filtering context.
 * @author jaubin
 */
public interface GojulFilter<S, T> {

    /**
     * Return {@code true} if {@code value} matches the accept criteria,
     * depending on the filtering context {@code context}, {@code false} otherwise.
     * Depending on your case you may need to use a context
     * or not. This filtering context can contain some additional information
     * used for filtering.
     *
     * @param value   the value to accept.
     * @param context the filtering context object. This parameter can be {@code null},
     *                depending on your actual needs.
     * @return {@code true} if {@code value} satisfies the accept criteria, {@code false} otherwise.
     * @throws NullPointerException if {@code value} is {@code null}.
     */
    boolean accept(final S value, final T context);
}
