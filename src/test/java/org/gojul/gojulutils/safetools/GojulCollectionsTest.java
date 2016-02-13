package org.gojul.gojulutils.safetools;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

public class GojulCollectionsTest {

	@Test
	public void testUnmodifiableList() {
		assertTrue(GojulCollections.unmodifiableList(null).isEmpty());
		assertEquals(Arrays.asList("foo", "bar"), GojulCollections.unmodifiableList(Arrays.asList("foo", "bar")));
	}

	@Test
	public void testUnmodifiableSet() {
		assertTrue(GojulCollections.unmodifiableSet(null).isEmpty());
		assertEquals(new HashSet<>(Arrays.asList("foo", "bar")), GojulCollections.unmodifiableSet(new HashSet<>(Arrays.asList("foo", "bar"))));
	}
}
