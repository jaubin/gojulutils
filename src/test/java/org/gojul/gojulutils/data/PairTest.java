package org.gojul.gojulutils.data;

import org.junit.Test;

import static org.junit.Assert.*;

public class PairTest {

    @Test
    public void testGetters() {
        Pair<String, Integer> p = new Pair<>("hello", 42);

        assertEquals("hello", p.getFirst());
        assertEquals(Integer.valueOf(42), p.getSecond());
    }

    @Test
    public void testEqualsHashCode() {
        assertEquals(new Pair<>("hello", "42").hashCode(), new Pair<>("hello", "42").hashCode());
        assertTrue(new Pair<>("hello", 42).equals(new Pair<>("hello", 42)));
        assertFalse(new Pair<>("hello", 42).equals(new Pair<String, Integer>("hello", null)));
        assertFalse(new Pair<>("hello", 42).equals(new Pair<String, Integer>(null, 42)));

    }

}