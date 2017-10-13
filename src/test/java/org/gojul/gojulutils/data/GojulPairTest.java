package org.gojul.gojulutils.data;

import org.junit.Test;

import static org.junit.Assert.*;

public class GojulPairTest {

    @Test
    public void testGetters() {
        GojulPair<String, Integer> p = new GojulPair<>("hello", 42);

        assertEquals("hello", p.getFirst());
        assertEquals(Integer.valueOf(42), p.getSecond());
    }

    @Test
    public void testEqualsHashCode() {
        assertEquals(new GojulPair<>("hello", "42").hashCode(), new GojulPair<>("hello", "42").hashCode());
        assertTrue(new GojulPair<>("hello", 42).equals(new GojulPair<>("hello", 42)));
        assertFalse(new GojulPair<>("hello", 42).equals(new GojulPair<String, Integer>("hello", null)));
        assertFalse(new GojulPair<>("hello", 42).equals(new GojulPair<String, Integer>(null, 42)));

    }

}