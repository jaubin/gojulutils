package org.gojul.gojulutils.safetools;

import static org.junit.Assert.*;
import static org.gojul.gojulutils.safetools.GojulDateFormatBuilder.*;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

import org.junit.Test;

public class GojulDateFormatBuilderTest {

	@Test(expected = NullPointerException.class)
	public void testConstructorWithNullFormat() {
		new GojulDateFormatBuilder(null);
	}
	
	@Test
	public void testBuilderWithAllFieldsSetToNull() {
		GojulDateFormatBuilder builder = new GojulDateFormatBuilder("yyyy/MM/dd")
				.setLocale(null)
				.setSymbols(null)
				.setTimeZone(null);
		
		assertEquals(Locale.getDefault(), builder.getLocale());
		assertEquals(TimeZone.getDefault(), builder.getTimeZone());
		assertNull(builder.getSymbols());
		
		assertEquals(new GojulDateFormatKey("yyyy/MM/dd", Locale.getDefault(), TimeZone.getDefault(), null), builder.toDateFormatKey());

	}
	
	@Test
	public void testBuilderWithAllFieldsSet() {
		DateFormatSymbols symbols = new DateFormatSymbols(Locale.CHINESE);
		GojulDateFormatBuilder builder = new GojulDateFormatBuilder("yyyy/MM/dd")
				.setLocale(Locale.ENGLISH)
				.setSymbols(symbols)
				.setTimeZone(TimeZone.getTimeZone("UTC"));
		
		assertNull(builder.getLocale());
		assertEquals(TimeZone.getTimeZone("UTC"), builder.getTimeZone());
		assertEquals(symbols, builder.getSymbols());
		
		assertEquals(new GojulDateFormatKey("yyyy/MM/dd", null, TimeZone.getTimeZone("UTC"), symbols), builder.toDateFormatKey());
		
	}
	
	@Test
	public void testBuilderWithAllFieldsSetExceptSymbols() {
		GojulDateFormatBuilder builder = new GojulDateFormatBuilder("yyyy/MM/dd")
				.setLocale(Locale.ENGLISH)
				.setSymbols(null)
				.setTimeZone(TimeZone.getTimeZone("UTC"));
		
		assertEquals(Locale.ENGLISH, builder.getLocale());
		assertEquals(TimeZone.getTimeZone("UTC"), builder.getTimeZone());
		
		assertEquals(new GojulDateFormatKey("yyyy/MM/dd", Locale.ENGLISH, TimeZone.getTimeZone("UTC"), null), builder.toDateFormatKey());
		
	}
	
	@Test
	public void testBuildWithAllFieldExceptTimeZoneSetUsesDateFormatSymbolsAndNotLocale() {
		DateFormatSymbols symbols = new DateFormatSymbols(Locale.CHINESE);
		GojulDateFormatBuilder builder = new GojulDateFormatBuilder("yyyy/MM/dd")
				.setLocale(Locale.ENGLISH)
				.setSymbols(symbols)
				.setTimeZone(null);
		
		SimpleDateFormat expected = new SimpleDateFormat("yyyy/MM/dd", symbols);
		expected.setTimeZone(TimeZone.getDefault());
		SimpleDateFormat actual = builder.build();
		
		assertEquals(expected, expected);
		assertEquals(expected.getDateFormatSymbols(), symbols);
		assertNotNull(expected.getTimeZone());
		assertEquals(expected.getTimeZone().getID(), actual.getTimeZone().getID());
		assertEquals(expected.toPattern(), actual.toPattern());
	}
	
	@Test
	public void testBuildWithSymbolsUnSetUsesLocale() {
		TimeZone tz = TimeZone.getTimeZone("UTC");
		GojulDateFormatBuilder builder = new GojulDateFormatBuilder("yyyy/MM/dd")
				.setLocale(Locale.ENGLISH)
				.setSymbols(null)
				.setTimeZone(tz);
		
		SimpleDateFormat expected = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
		expected.setTimeZone(tz);
		
		SimpleDateFormat actual = builder.build();
		
		assertEquals(expected, builder.build());
		assertNotNull(expected.getTimeZone());
		assertEquals(expected.getTimeZone().getID(), actual.getTimeZone().getID());
		assertEquals(expected.toPattern(), actual.toPattern());
	}

}
