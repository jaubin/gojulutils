package org.gojul.gojulutils.safetools;

import static org.junit.Assert.*;
import static org.gojul.gojulutils.safetools.GojulUnboxingUtils.*;

import org.junit.Test;

public class GojulUnboxingUtilsTest {

	@Test
	public void testSafeUnboxBoolean() {
		assertFalse(safeUnboxBoolean(null));
		assertFalse(safeUnboxBoolean(Boolean.FALSE));
		assertTrue(safeUnboxBoolean(Boolean.TRUE));
	}

	@Test
	public void testSafeUnboxChar() {
		assertEquals('a', safeUnboxChar(null, 'a'));
		assertEquals('c', safeUnboxChar(Character.valueOf('c'), 'a'));
	}
}
