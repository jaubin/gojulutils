package org.gojul.gojulutils.safetools;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.concurrent.ConcurrentHashMap;

import org.gojul.gojulutils.validation.GojulPreconditions;

/**
 * <p>
 * It is widely known that Java {@link DecimalFormat} instances are costly to instanciate
 * and not thread-safe, causing some performance issues. This class aims to partly solve the
 * issue by caching created {@link DecimalFormat} instances and using them in a thread safe
 * fashion.
 * </p>
 * <p>
 * Note that this class does not declare parse methods of {@link DecimalFormat} instances currently
 * because they're only seldom used..
 * </p>
 * 
 * @author julien
 *
 */
public class GojulDecimalFormatter {

	private final static class DecimalFormatKey {
		
		private String format;
		private DecimalFormatSymbols symbols;
		
		public DecimalFormatKey(final String format, final DecimalFormatSymbols symbols) {
			this.format = format;
			this.symbols = symbols;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((format == null) ? 0 : format.hashCode());
			result = prime * result + ((symbols == null) ? 0 : symbols.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			DecimalFormatKey other = (DecimalFormatKey) obj;
			if (format == null) {
				if (other.format != null)
					return false;
			} else if (!format.equals(other.format))
				return false;
			if (symbols == null) {
				if (other.symbols != null)
					return false;
			} else if (!symbols.equals(other.symbols))
				return false;
			return true;
		}
		
	}
	
	private final static ConcurrentHashMap<String, DecimalFormat> DECIMAL_FORMATS_BY_FORMAT = new ConcurrentHashMap<>();
	
	private final static ConcurrentHashMap<DecimalFormatKey, DecimalFormat> DECIMAL_FORMATS_BY_KEY = new ConcurrentHashMap<>();
	
	/**
	 * Private constructor. Prevents class from being instanciated.
	 */
	private GojulDecimalFormatter() {}
	
	/**
	 * Format value {@code d} using format {@code format}.
	 * @param format the format to apply.
	 * @param d the value to format.
	 * @return the formatted value.
	 * 
	 * @throws NullPointerException if {@code format} is {@code null}.
	 */
	public static String format(final String format, final double d) {
		GojulPreconditions.checkNotNull(format, "format is null");
		DecimalFormat df = retrieveFromFormat(format);
		// It's not a big issue here to use synchronized block because the
		// JVM has been highly optimized to handle this.
		synchronized(df) {
			return df.format(d);
		}
	}
	
	/**
	 * Format value {@code l} using format {@code format}.
	 * @param format the format to apply.
	 * @param l the value to format.
	 * @return the formatted value.
	 * 
	 * @throws NullPointerException if {@code format} is {@code null}.
	 */
	public static String format(final String format, final long l) {
		GojulPreconditions.checkNotNull(format, "format is null");
		DecimalFormat df = retrieveFromFormat(format);
		// It's not a big issue here to use synchronized block because the
		// JVM has been highly optimized to handle this.
		synchronized(df) {
			return df.format(l);
		}
	}
	
	private static DecimalFormat retrieveFromFormat(final String format) {
		DecimalFormat df = DECIMAL_FORMATS_BY_FORMAT.get(format);
		if (df == null) {
			df = new DecimalFormat(format);
			DecimalFormat cached = DECIMAL_FORMATS_BY_FORMAT.putIfAbsent(format, df);
			df = cached == null ? df: cached;
		}
		return df;
	}
	
	/**
	 * Format value {@code d} using format {@code format} and format symbols {@code symbols}.
	 * @param format the format to apply.
	 * @param symbols the symbols to use for formatting.
	 * @param d the value to format.
	 * @return the formatted value.
	 * 
	 * @throws NullPointerException if {@code format} or {@code symbols} is {@code null}.
	 */
	public static String format(final String format, final DecimalFormatSymbols symbols, final double d) {
		GojulPreconditions.checkNotNull(format, "format is null");
		GojulPreconditions.checkNotNull(symbols, "symbols is null");
		DecimalFormat df = retrieveFromKey(format, symbols);
		// It's not a big issue here to use synchronized block because the
		// JVM has been highly optimized to handle this.
		synchronized(df) {
			return df.format(d);
		}
	}
	
	/**
	 * Format value {@code l} using format {@code format} and format symbols {@code symbols}.
	 * @param format the format to apply.
	 * @param symbols the symbols to use for formatting.
	 * @param l the value to format.
	 * @return the formatted value.
	 * 
	 * @throws NullPointerException if {@code format} or {@code symbols} is {@code null}.
	 */
	public static String format(final String format, final DecimalFormatSymbols symbols, final long l) {
		GojulPreconditions.checkNotNull(format, "format is null");
		GojulPreconditions.checkNotNull(symbols, "symbols is null");
		DecimalFormat df = retrieveFromKey(format, symbols);
		// It's not a big issue here to use synchronized block because the
		// JVM has been highly optimized to handle this.
		synchronized(df) {
			return df.format(l);
		}
	}
	
	private static DecimalFormat retrieveFromKey(final String format, final DecimalFormatSymbols symbols) {
		DecimalFormatKey key = new DecimalFormatKey(format, symbols);
		DecimalFormat df = DECIMAL_FORMATS_BY_KEY.get(symbols);
		if (df == null) {
			df = new DecimalFormat(format, symbols);
			DecimalFormat cached = DECIMAL_FORMATS_BY_KEY.putIfAbsent(key, df);
			df = cached == null ? df: cached;
		}
		return df;
	}
}
