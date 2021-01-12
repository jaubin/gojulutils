package org.gojul.gojulutils.safetools;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class GojulEncapsulationUtilsTest {

    @Test
    public void testUnmodifiableList() {
        assertNull(GojulEncapsulationUtils.unmodifiableList(null));
        List<String> expected = Arrays.asList("foo", "bar");
        assertEquals(expected, GojulEncapsulationUtils.unmodifiableList(expected));
        assertNotSame(expected, GojulEncapsulationUtils.unmodifiableList(expected));
    }

    @Test
    public void testUnmodifiableSet() {
        assertNull(GojulEncapsulationUtils.unmodifiableSet(null));
        Set<String> expected = new HashSet<>(Arrays.asList("foo", "bar"));
        assertEquals(expected, GojulEncapsulationUtils.unmodifiableSet(expected));
        assertNotSame(expected, GojulEncapsulationUtils.unmodifiableSet(expected));
    }

    @Test
    public void testUnmodifiableCollection() {
        assertNull(GojulEncapsulationUtils.unmodifiableCollection(null));
        List<String> expected = Arrays.asList("foo", "bar");

        Collection<String> found = GojulEncapsulationUtils.unmodifiableCollection(Arrays.asList("foo", "bar"));
        assertEquals(expected.size(), found.size());
        assertTrue(expected.containsAll(found));
        assertTrue(found.containsAll(expected));
        assertNotSame(found, expected);
    }

    @Test
    public void testUnmodifiableMap() {
        assertNull(GojulEncapsulationUtils.unmodifiableMap(null));

        Map<String, String> expected = new HashMap<>();
        expected.put("foo", "bar");
        expected.put("hello", "world");
        assertEquals(expected, GojulEncapsulationUtils.unmodifiableMap(expected));
        assertNotSame(expected, GojulEncapsulationUtils.unmodifiableMap(expected));
    }

    @Test
    public void testCopyDate() {
        assertNull(GojulEncapsulationUtils.copyDate(null));

        Date d = new Date();
        assertEquals(d, GojulEncapsulationUtils.copyDate(d));
        assertNotSame(d, GojulEncapsulationUtils.copyDate(d));
    }

    @Test
    public void testCopyArray() {
        assertNull(GojulEncapsulationUtils.copyArray(null));
        assertArrayEquals(new String[0], GojulEncapsulationUtils.copyArray(new String[0]));

        String[] sample = {"foo", "bar"};
        assertArrayEquals(sample, GojulEncapsulationUtils.copyArray(sample));
        assertNotSame(sample, GojulEncapsulationUtils.copyArray(sample));
    }

    @Test(expected = NullPointerException.class)
    public void testDeepCopyArrayWithNullCopyFunctionThrowsException() {
        GojulEncapsulationUtils.deepCopyArray(new String[]{"foo", "bar"}, null);
    }

    @Test
    public void testDeepCopyArray() {
        GojulEncapsulationUtils.GojulCopyFunction<Date> copyFunction = new GojulEncapsulationUtils.GojulCopyFunction<Date>() {

            @Override
            public Date copy(Date elem) {
                return (Date) elem.clone();
            }
        };

        assertNull(GojulEncapsulationUtils.deepCopyArray(null, copyFunction));
        assertArrayEquals(new Date[0], GojulEncapsulationUtils.deepCopyArray(new Date[0], copyFunction));

        Date d1 = new Date();
        Date d2 = new Date(d1.getTime() + 1000L);

        // We put the null value to ensure the method is safe against null values.
        Date[] sample = {d1, null, d2};

        Date[] copy = GojulEncapsulationUtils.deepCopyArray(sample, copyFunction);
        assertArrayEquals(sample, copy);
        assertNotSame(sample, copy);

        for (int i = 0, len = sample.length; i < len; i++) {
            if (sample[i] == null) {
                assertNull(copy[i]);
            } else {
                assertNotSame(copy[i], sample[i]);
            }
        }
    }
}
