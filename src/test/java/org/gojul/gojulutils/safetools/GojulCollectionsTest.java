package org.gojul.gojulutils.safetools;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

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
	
	@Test
	public void testUnmodifiableCollection() {
		assertTrue(GojulCollections.unmodifiableCollection(null).isEmpty());
		List<String> expected = Arrays.asList("foo", "bar");
		
		Collection<String> found = GojulCollections.unmodifiableCollection(Arrays.asList("foo", "bar"));
		assertEquals(expected.size(), found.size());
		assertTrue(expected.containsAll(found));
		assertTrue(found.containsAll(expected));
	}
}
