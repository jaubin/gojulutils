package org.gojul.gojulutils.safetools;

import org.junit.Test;

import java.text.DecimalFormatSymbols;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

public class GojulDecimalFormatterTest {

    @Test(expected = NullPointerException.class)
    public void testFormatDoubleWithNullFormatThrowsException() {
        GojulDecimalFormatter.format(null, 1.345d);
    }

    @Test
    public void testFormatDouble() {
        Locale locale = Locale.getDefault();
        // For test portability
        Locale.setDefault(Locale.ENGLISH);
        try {
            assertEquals("01.345", GojulDecimalFormatter.format("00.000", 1.345d));
        } finally {
            Locale.setDefault(locale);
        }
    }

    @Test(expected = NullPointerException.class)
    public void testFormatLongWithNullFormatThrowsException() {
        GojulDecimalFormatter.format(null, 1345);
    }

    @Test
    public void testFormatLong() {
        Locale locale = Locale.getDefault();
        // For test portability
        Locale.setDefault(Locale.ENGLISH);
        try {
            assertEquals("01345.000", GojulDecimalFormatter.format("00000.000", 1345L));
        } finally {
            Locale.setDefault(locale);
        }
    }

    @Test(expected = NullPointerException.class)
    public void testFormatDoubleWithSymbolsWithNullFormatThrowsException() {
        GojulDecimalFormatter.format(null, new DecimalFormatSymbols(), 1.345d);
    }

    @Test(expected = NullPointerException.class)
    public void testFormatDoubleWithSymbolsWithNullSymbolsThrowsException() {
        GojulDecimalFormatter.format("00.000", null, 1.345d);
    }

    @Test
    public void testFormatDoubleWithSymbols() {
        assertEquals("01.345", GojulDecimalFormatter.format("00.000", new DecimalFormatSymbols(Locale.ENGLISH), 1.345d));
        // Test the cache
        assertEquals("01.345", GojulDecimalFormatter.format("00.000", new DecimalFormatSymbols(Locale.ENGLISH), 1.345d));
    }

    @Test(expected = NullPointerException.class)
    public void testFormatLongWithSymbolsWithNullFormatThrowsException() {
        GojulDecimalFormatter.format(null, new DecimalFormatSymbols(), 1.345d);
    }

    @Test(expected = NullPointerException.class)
    public void testFormatLongWithSymbolsWithNullSymbolsThrowsException() {
        GojulDecimalFormatter.format("00.000", null, 1345);
    }

    @Test
    public void testFormatLongWithSymbols() {
        assertEquals("01345.000", GojulDecimalFormatter.format("00000.000", new DecimalFormatSymbols(Locale.ENGLISH), 1345L));
        // Test the cache
        assertEquals("01345.000", GojulDecimalFormatter.format("00000.000", new DecimalFormatSymbols(Locale.ENGLISH), 1345L));
    }
}
