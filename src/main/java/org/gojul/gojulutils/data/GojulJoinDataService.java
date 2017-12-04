package org.gojul.gojulutils.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * Class {@code GojulJoinDataService} is a service which
 * has for purpose to emulate JOIN operations. In case you're
 * using Spring you should simply wrap this service within
 * a decorator (or something else) with the proper service
 * annotation to integrate it directly within your beans.
 * </p>
 * <p>The goal here is to help avoiding using the database
 * notably when running algorithms on CSV files which behave
 * as a read-only database. It can be extremely efficient because
 * database imports tend to be quite costly, and then data queries
 * are even more. However these algorithms are not suited for very
 * huge amounts of data, because of memory constraints. It is safe
 * to consider they're fine for joinings for files which have a size
 * up to roughly 100k lines.</p>
 *
 *
 * @author julien
 */
public class GojulJoinDataService {

    /**
     * Constructor.
     */
    public GojulJoinDataService() {

    }


    /**
     * Perform a LEFT JOIN using the data contained in {@code joinData}. Note that elements
     * on the left which have a {@code null} key are attached in the resulting pair list to
     * the elements which have a {@code null} key on the right.
     *
     * @param joinData the object which contains the elements used to perform the JOIN operation.
     * @param <K> the JOIN key type.
     * @param <S> the type of the left elements to join.
     * @param <T> the type of the right elements to join.
     * @return a list of pair of elements. Each of them binds an element of the left to either an
     * element of the right, or {@code null} as it is a left join.
     *
     * @throws NullPointerException if {@code joinData} is {@code null}.
     */
    public <K, S, T> List<GojulPair<S, T>> leftJoin(final GojulJoinData<K, S, T> joinData) {
        Objects.requireNonNull(joinData, "joinData is null");

        Map<K, List<S>> leftElements = joinData.getLeftElementsPerKey();
        Map<K, List<T>> rightElements = joinData.getRightElementsPerKey();

        List<GojulPair<S, T>> result = new ArrayList<>();

        for (Map.Entry<K, List<S>> entry: leftElements.entrySet()) {
            result.addAll(joinForKey(entry.getKey(), entry.getValue(), rightElements));
        }

        return result;
    }

    private <K, S, T> List<GojulPair<S, T>> joinForKey(final K key, final Iterable<S> elements,
                                                       final Map<K, List<T>> elementsToJoin) {
        List<T> targetElements = elementsToJoin.get(key);

        List<GojulPair<S, T>> result = new ArrayList<>();

        for (S element: elements) {
            if (targetElements == null) {
                result.add(new GojulPair<>(element, (T) null));
            } else {
                for (T targetElement: targetElements) {
                    result.add(new GojulPair<>(element, targetElement));
                }
            }
        }

        return result;
    }

    /**
     * Perform an INNER JOIN using the data contained in {@code joinData}. Note that elements
     * on the left which have a {@code null} key are attached in the resulting pair list to
     * the elements which have a {@code null} key on the right.
     *
     * @param joinData the object which contains the elements used to perform the JOIN operation.
     * @param <K> the JOIN key type.
     * @param <S> the type of the left elements to join.
     * @param <T> the type of the right elements to join.
     * @return a list of pair of elements. Each of them binds an element of the left to an
     * element of the right.
     *
     * @throws NullPointerException if {@code joinData} is {@code null}.
     */
    public <K, S, T> List<GojulPair<S, T>> innerJoin(final GojulJoinData<K, S, T> joinData) {
        Objects.requireNonNull(joinData, "joinData is null");

        List<GojulPair<S, T>> result = leftJoin(joinData).stream()
                .filter(e -> e.getSecond() != null).collect(Collectors.toList());

        return result;
    }
}
