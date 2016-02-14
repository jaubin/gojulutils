package org.gojul.gojulutils.safetools;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

import org.gojul.gojulutils.validation.GojulPreconditions;

/**
 * Class {@code GojulDateFormatBuilder} builds {@link DateFormat} instances. This class
 * is pointless if not used with {@link GojulDateFormatter} class.
 * 
 * @author julien
 *
 * @see org.gojul.gojulutils.safetools.GojulDateFormatter
 */
public class GojulDateFormatBuilder {
	
	private String format;
	private TimeZone timeZone;
	private Locale locale;
	private DateFormatSymbols symbols;
	
	/**
	 * Constructor.
	 * @param format the format string to use, as specified in
	 * {@link SimpleDateFormat} specification.
	 * 
	 * @throws NullPointerException if {@code format} is {@code null}.
	 */
	public GojulDateFormatBuilder(final String format) {
		GojulPreconditions.checkNotNull(format, "format is null");
		this.format = format;
	}
	
	/**
	 * Set the resulting {@link DateFormat} time zone. If this field is not
	 * set or {@code null} the default {@link TimeZone} is used.
	 * @param tz the {@link TimeZone} to use.
	 * @return the builder instance.
	 */
	public GojulDateFormatBuilder setTimeZone(final TimeZone tz) {
		if (tz != null) {
			// Defensive copy.
			this.timeZone = (TimeZone) tz.clone();
		}
		return this;
	}
	
	/**
	 * Set the resulting {@link DateFormat} locale. If this field is not
	 * set or {@code null} the default {@link Locale} will be used instead.
	 * If method {@link GojulDateFormatBuilder#setSymbols(DateFormatSymbols)}
	 * is used with a non-null {@link DateFormatSymbols} instance this parameter
	 * will just be ignored.
	 * @param locale the {@link locale} to set.
	 * @return the builder instance.
	 */
	public GojulDateFormatBuilder setLocale(final Locale locale) {
		if (locale != null) {
			// Defensive copy.
			this.locale = (Locale) locale.clone();
		}
		return this;
	}
	
	/**
	 * Set the resulting {@link DateFormat} symbols. If this field is set it overrides
	 * the {@link Locale} set using method {@link GojulDateFormatBuilder#setLocale(Locale)},
	 * but if it is not set the {@link Locale} is used instead.
	 * @param symbols the symbols to set.
	 * @return the builder instance.
	 */
	public GojulDateFormatBuilder setSymbols(final DateFormatSymbols symbols) {
		if (symbols != null) {
			// Defensive copy.
			this.symbols = (DateFormatSymbols) symbols.clone();
		}
		return this;
	}
	
	/**
	 * Return the {@link Locale} used for this builder. This method is used
	 * for internal and testing purposes only.
	 * @return the {@link Locale} used for this builder.
	 */
	Locale getLocale() {
		return this.locale == null ? Locale.getDefault(): this.locale;
	}
	
	/**
	 * Return the {@link TimeZone} used for this builder. This method is used
	 * for internal and testing purposes only.
	 * @return the {@link TimeZone} used for this builder.
	 */
	TimeZone getTimeZone() {
		return this.timeZone == null ? TimeZone.getDefault(): this.timeZone;
	}
	
	/**
	 * Return the {@link DateFormatSymbols} used for this builder. This method
	 * is used for internal and testing purposes only.
	 * @return the {@link DateFormatSymbols} used for this builder.
	 */
	DateFormatSymbols getSymbols() {
		return this.symbols;
	}
	
	/**
	 * Create the resulting {@link DateFormat}.
	 * @return the resulting {@link DateFormat}.
	 */
	public SimpleDateFormat build() {
		SimpleDateFormat result = null;
		if (symbols != null) {
			result = new SimpleDateFormat(format, symbols);
		} else {
			result = new SimpleDateFormat(format, getLocale());
		}
		result.setTimeZone(getTimeZone());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((format == null) ? 0 : format.hashCode());		
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GojulDateFormatBuilder other = (GojulDateFormatBuilder) obj;
		if (format == null) {
			if (other.format != null)
				return false;
		} else if (!format.equals(other.format))
			return false;
		if (locale == null) {
			if (other.locale != null)
				return false;
		} else if (!locale.equals(other.locale))
			return false;
		if (symbols == null) {
			if (other.symbols != null)
				return false;
		} else if (!symbols.equals(other.symbols))
			return false;
		if (timeZone == null) {
			if (other.timeZone != null)
				return false;
		// We compare the timezone Ids because timezone instances rely
		// on reference identity rather than equals/hashcode.
		// Thus TimeZone IDs cannot be null.
		} else if (!timeZone.getID().equals(other.timeZone.getID()))
			return false;
		return true;
	}
}
