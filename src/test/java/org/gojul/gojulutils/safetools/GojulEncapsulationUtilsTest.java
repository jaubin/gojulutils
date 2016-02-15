package org.gojul.gojulutils.safetools;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

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
}
