package org.gojul.gojulutils.data;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class GojulJoinDataTest {

    private final GojulJoinData.GojulJoinDataKey<String, String> keyLeft = e -> e.substring(0, 1);
    private final GojulJoinData.GojulJoinDataKey<String, Integer> keyRight = e -> String.valueOf(e).substring(0, 1);

    private final List<String> leftData = Collections.unmodifiableList(Arrays.asList("123", "10", "20", "25", "35"));
    private final List<Integer> rightData = Collections.unmodifiableList(Arrays.asList(143, 100, 232, 275, 321));

    @Test(expected = NullPointerException.class)
    public void testConstructorWithNullLeftKeyThrowsException() {
        new GojulJoinData<>(null, leftData, keyRight, rightData);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorWithNullLeftDataThrowsException() {
        new GojulJoinData<>(keyLeft, null, keyRight, rightData);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorWithNullRightKeyThrowsException() {
        new GojulJoinData<>(keyLeft, leftData, null, rightData);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorWithNullRightDataThrowsException() {
        new GojulJoinData<>(keyLeft, leftData, keyRight, null);
    }

    @Test
    public void testAll() {
        GojulJoinData<String, String, Integer> joinData = new GojulJoinData<>(keyLeft, leftData, keyRight, rightData);

        Map<String, List<String>> expectedLeft = new HashMap<>();
        expectedLeft.put("1", Arrays.asList("123", "10"));
        expectedLeft.put("2", Arrays.asList("20", "25"));
        expectedLeft.put("3", Arrays.asList("35"));

        Map<String, List<Integer>> expectedRight = new HashMap<>();
        expectedRight.put("1", Arrays.asList(143, 100));
        expectedRight.put("2", Arrays.asList(232, 275));
        expectedRight.put("3", Arrays.asList(321));

        assertEquals(expectedLeft, joinData.getLeftElementsPerKey());
        assertEquals(expectedRight, joinData.getRightElementsPerKey());
    }
}