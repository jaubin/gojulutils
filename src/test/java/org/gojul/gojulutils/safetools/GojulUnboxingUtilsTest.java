package org.gojul.gojulutils.safetools;

import static org.junit.Assert.*;
import static org.gojul.gojulutils.safetools.GojulUnboxingUtils.*;

import org.junit.Test;

public class GojulUnboxingUtilsTest {

	@Test
	public void testUnboxBoolean() {
		assertFalse(unboxBoolean(null));
		assertFalse(unboxBoolean(Boolean.FALSE));
		assertTrue(unboxBoolean(Boolean.TRUE));
	}

	@Test
	public void testUnboxChar() {
		assertEquals('a', unboxChar(null, 'a'));
		assertEquals('c', unboxChar(Character.valueOf('c'), 'a'));
	}
	
	@Test
	public void testUnboxByte() {
		assertEquals((byte) 12, unboxByte(null, (byte) 12));
		assertEquals((byte) 15, unboxByte(Byte.valueOf((byte) 15), (byte) 12));
	}
	
	@Test
	public void testUnboxShort() {
		assertEquals((short) 12, unboxShort(null, (short) 12));
		assertEquals((short) 15, unboxShort(Short.valueOf((short) 15), (short) 12));
	}
	
	@Test
	public void testUnboxInt() {
		assertEquals(12, unboxInt(null, 12));
		assertEquals(15, unboxInt(Integer.valueOf(15), 12));
	}
	
	@Test
	public void testUnboxLong() {
		assertEquals(12L, unboxLong(null, 12L));
		assertEquals(15L, unboxLong(Long.valueOf(15), 12L));
	}
	
	@Test
	public void testUnboxFloat() {
		assertEquals(12f, unboxFloat(null, 12f), 0.05f);
		assertEquals(15f, unboxFloat(Float.valueOf(15f), 12f), 0.05f);
	}
	
	@Test
	public void testUnboxDouble() {
		assertEquals(12d, unboxDouble(null, 12d), 0.05d);
		assertEquals(15d, unboxDouble(Double.valueOf(15d), 12d), 0.05d);
	}
}
