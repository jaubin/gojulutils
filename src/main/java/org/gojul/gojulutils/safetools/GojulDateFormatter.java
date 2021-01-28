package org.gojul.gojulutils.safetools;

import org.gojul.gojulutils.safetools.GojulDateFormatBuilder.GojulDateFormatKey;
import org.gojul.gojulutils.validation.GojulPreconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * <p>
 * Many applications still rely on the old {@link SimpleDateFormat} class to format date.
 * The problem with this class is that it is not thread-safe, and thus it is costly to instanciate.
 * This class aims to tackle this issue by caching the {@link SimpleDateFormat} instances and
 * call them in a thread-safe way.
 * </p>
 * <p>
 * Not all the capabilities of {@link SimpleDateFormat} have been implemented there, only the most
 * commonly used ones.
 * </p>
 *
 * @author julien
 */
public class GojulDateFormatter {

    private final static Logger log = LoggerFactory.getLogger(GojulDateFormatter.class);

    private final static ConcurrentMap<GojulDateFormatKey, SimpleDateFormat> DATE_FORMATS_BY_KEY = new ConcurrentHashMap<>();

    private GojulDateFormatter() {
        // Private constructor. Prevents class from
        // being instanciated from the outside.
        throw new IllegalStateException("Shoo away !!!");
    }

    /**
     * Format the date {@code d} using the format string {@code formatString}. This method is a simple shortcut
     * to :
     * <pre>
     * <code>
     * GojulDateFormatter.format(new GojulDateFormatBuilder(string), date);
     * </code>
     * </pre>
     *
     * @param formatString the format string used to perform the parsing. See the specification of
     *                     {@link SimpleDateFormat} to check its syntax.
     * @param d            the date to format.
     * @return the date {@code d} formatted as a {@link String} using the format string {@code s}.
     * @throws NullPointerException     if any of the method parameters is {@code null}.
     * @throws IllegalArgumentException if the format string {@code formatString} is an invalid
     *                                  one.
     */
    public static String format(final String formatString, final Date d) {
        Objects.requireNonNull(formatString, "formatString is null");
        Objects.requireNonNull(d, "d is null");
        return format(new GojulDateFormatBuilder(formatString), d);
    }

    /**
     * Format the date {@code d} using the date format characteristics supplied by {@code builder}.
     *
     * @param builder the builder instance used to instanciate the right {@link SimpleDateFormat}
     *                if necessary.
     * @param d       the date to format.
     * @return the date {@code d} formatted as a {@link String} using the date format characteristics
     * supplied by {@code builder}.
     * @throws NullPointerException     if any of the method parameters is {@code null}.
     * @throws IllegalArgumentException if the date format supplied by {@code builder} is an invalid
     *                                  one.
     */
    public static String format(final GojulDateFormatBuilder builder, final Date d) {
        Objects.requireNonNull(builder, "builder is null");
        Objects.requireNonNull(d, "d is null");
        SimpleDateFormat sdf = getOrCreateDateFormat(builder);
        synchronized (sdf) {
            return sdf.format(d);
        }
    }

    /**
     * <p>
     * Parse the date from {@link String} {@code s} using the date format string
     * {@code formatString}, and return the corresponding {@link Date} instance.
     * Note that if an error occurs while parsing the date this method simply returns {@code null}
     * instead of causing you to manage a {@link ParseException}. If {@code null} values are forbidden
     * in your code just use {@link GojulPreconditions#checkAssertion(boolean, String)} to test this
     * in a declarative way.
     * </p>
     * <p>
     * This method is a simple shortcut to :
     * <pre>
     * <code>
     * GojulDateFormatter.parse(new GojulDateFormatBuilder(format), s);
     * </code>
     * </pre>
     *
     * @param formatString the format string used to perform the parsing. See the specification of
     *                     {@link SimpleDateFormat} to check its syntax.
     * @param s            the date to parse.
     * @return the date from {@link String} {@code s} using the date format string
     * {@code formatString}, and return the corresponding {@link Date} instance, or
     * {@code null} if a parse error occurs.
     * @throws NullPointerException     if any of the method parameters is {@code null}.
     * @throws IllegalArgumentException if the format string {@code formatString} is an invalid
     *                                  one.
     */
    public static Date parse(final String formatString, final String s) {
        Objects.requireNonNull(formatString, "formatString is null");
        Objects.requireNonNull(s, "s is null");
        return parse(new GojulDateFormatBuilder(formatString), s);
    }

    /**
     * Parse the date from {@link String} {@code s} using the date format characteristics
     * supplied with {@code builder}, and return the corresponding {@link Date} instance.
     * Note that if an error occurs while parsing the date this method simply returns {@code null}
     * instead of causing you to manage a {@link ParseException}. If {@code null} values are forbidden
     * in your code just use {@link GojulPreconditions#checkAssertion(boolean, String)} to test this
     * in a declarative way.
     *
     * @param builder the builder instance used to instanciate the right {@link SimpleDateFormat}
     *                if necessary.
     * @param s       the date to parse.
     * @return the date from {@link String} {@code s} using the date format characteristics
     * supplied with {@code builder}, and return the corresponding {@link Date} instance, or
     * {@code null} if a parse error occurs.
     * @throws NullPointerException     if any of the method parameters is {@code null}.
     * @throws IllegalArgumentException if the date format supplied by {@code builder} is an invalid
     *                                  one.
     */
    public static Date parse(final GojulDateFormatBuilder builder, final String s) {
        Objects.requireNonNull(builder, "builder is null");
        Objects.requireNonNull(s, "s is null");
        SimpleDateFormat sdf = getOrCreateDateFormat(builder);
        synchronized (sdf) {
            try {
                return sdf.parse(s);
            } catch (ParseException e) {
                log.error(String.format("Error while parsing date string %s with date format %s", s, sdf.toPattern()), e);
                return null;
            }
        }
    }

    private static SimpleDateFormat getOrCreateDateFormat(final GojulDateFormatBuilder builder) {
        GojulDateFormatKey key = builder.toDateFormatKey();
        SimpleDateFormat result = DATE_FORMATS_BY_KEY.get(key);
        if (result == null) {
            result = builder.build();
            SimpleDateFormat sdf = DATE_FORMATS_BY_KEY.putIfAbsent(key, result);
            result = sdf == null ? result : sdf;
        }
        return result;
    }
}
