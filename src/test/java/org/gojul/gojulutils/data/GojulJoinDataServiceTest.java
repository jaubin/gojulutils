package org.gojul.gojulutils.data;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class GojulJoinDataServiceTest {

    private final GojulJoinData.GojulJoinDataKey<String, String> keyLeft = e -> e.substring(0, 1);
    private final GojulJoinData.GojulJoinDataKey<String, Integer> keyRight = e -> String.valueOf(e).substring(0, 1);

    private final List<String> leftData = Collections.unmodifiableList(Arrays.asList("123", "10", "20", "25", "35"));
    private final List<Integer> rightData = Collections.unmodifiableList(Arrays.asList(143, 100, 232, 275, 521));

    private GojulJoinDataService service;

    @Before
    public void setup() {
        service = new GojulJoinDataService();
    }

    @Test(expected = NullPointerException.class)
    public void testLeftJoinWithNullDataThrowsException() throws Exception {
        service.leftJoin(null);
    }

    @Test
    public void testLeftJoin() throws Exception {
        Set<GojulPair<String, Integer>> expected = new HashSet<>(Arrays.asList(
                new GojulPair<>("123", 143),
                new GojulPair<>("123", 100),
                new GojulPair<>("10", 143),
                new GojulPair<>("10", 100),
                new GojulPair<>("20", 232),
                new GojulPair<>("20", 275),
                new GojulPair<>("25", 232),
                new GojulPair<>("25", 275),
                new GojulPair<>("35", null)
        ));

        assertEquals(expected, new HashSet<>(service.leftJoin(new GojulJoinData<>(keyLeft, leftData,
                keyRight, rightData))));
    }

    @Test(expected = NullPointerException.class)
    public void testInnerJoinWithNullDataThrowsException() throws Exception {
        service.innerJoin(null);
    }

    @Test
    public void testInnerJoin() throws Exception {
        Set<GojulPair<String, Integer>> expected = new HashSet<>(Arrays.asList(
                new GojulPair<>("123", 143),
                new GojulPair<>("123", 100),
                new GojulPair<>("10", 143),
                new GojulPair<>("10", 100),
                new GojulPair<>("20", 232),
                new GojulPair<>("20", 275),
                new GojulPair<>("25", 232),
                new GojulPair<>("25", 275)
        ));

        assertEquals(expected, new HashSet<>(service.innerJoin(new GojulJoinData<>(keyLeft, leftData,
                keyRight, rightData))));
    }

}