package org.gojul.gojulutils.safetools;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.junit.Test;

public class GojulDateFormatterTest {

	@Test(expected = NullPointerException.class)
	public void testParseWithNullBuilderThrowsException() {
		GojulDateFormatter.parse(null, "hello");
	}

	@Test(expected = NullPointerException.class)
	public void testParseWithStringThrowsException() {
		GojulDateFormatter.parse(new GojulDateFormatBuilder("yyyy/MM/dd"), null);
	}
	
	@Test
	public void testParseWithInvalidParseStringReturnsNull() {
		assertNull(GojulDateFormatter.parse(new GojulDateFormatBuilder("yyyy/MM/dd"), "hello"));
	}
	
	@Test
	public void testParse() throws Exception {
		GojulDateFormatBuilder builder = new GojulDateFormatBuilder("yyyy/MM/dd hh:mm:ss")
				.setLocale(Locale.ENGLISH)
				.setTimeZone(TimeZone.getTimeZone("UTC"));
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss", Locale.ENGLISH);
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		
		String s = "1983/03/29 09:50:00";
		Date expected = sdf.parse(s);
		
		assertEquals(expected, GojulDateFormatter.parse(builder, s));
		// Test the cache.
		assertEquals(expected, GojulDateFormatter.parse(builder, s));
	}
}