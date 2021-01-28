package org.gojul.gojulutils.safetools;

/**
 * While autoboxing is a safe process, this is not the case of unboxing. The goal of this
 * class is to make unboxing safe by performing null checks and returning default value
 * when necessary.
 *
 * @author julien
 */
public class GojulUnboxingUtils {

    private GojulUnboxingUtils() {
        // Private constructor. Prevents class from
        // being instanciated from the outside.
        throw new IllegalStateException("Shoo away !!!");
    }

    /**
     * Return {@code true} if {@code b} equals {@link Boolean#TRUE}, {@code false} otherwise.
     *
     * @param b the object to unbox.
     * @return {@code true} if {@code b} equals {@link Boolean#TRUE}, {@code false} otherwise.
     */
    public static boolean unboxBoolean(final Boolean b) {
        return Boolean.TRUE.equals(b);
    }

    /**
     * Return the unboxed flavour of {@code c} if {@code c} is not {@code null}, {@code defaultValue}
     * otherwise.
     *
     * @param c            the {@link Character} instance to unbox.
     * @param defaultValue the default value provided if {@code c} is {@code null}.
     * @return the unboxed flavour of {@code c} if {@code c} is not {@code null}, {@code defaultValue}
     * otherwise.
     */
    public static char unboxChar(final Character c, final char defaultValue) {
        return c == null ? defaultValue : c.charValue();
    }

    /**
     * Return the unboxed flavour of {@code b} if {@code b} is not {@code null}, {@code defaultValue}
     * otherwise.
     *
     * @param b            the {@link Byte} instance to unbox.
     * @param defaultValue the default value provided if {@code b} is {@code null}.
     * @return the unboxed flavour of {@code b} if {@code b} is not {@code null}, {@code defaultValue}
     * otherwise.
     */
    public static byte unboxByte(final Byte b, final byte defaultValue) {
        return b == null ? defaultValue : b.byteValue();
    }

    /**
     * Return the unboxed flavour of {@code s} if {@code s} is not {@code null}, {@code defaultValue}
     * otherwise.
     *
     * @param s            the {@link Short} instance to unbox.
     * @param defaultValue the default value provided if {@code s} is {@code null}.
     * @return the unboxed flavour of {@code s} if {@code s} is not {@code null}, {@code defaultValue}
     * otherwise.
     */
    public static short unboxShort(final Short s, final short defaultValue) {
        return s == null ? defaultValue : s.shortValue();
    }

    /**
     * Return the unboxed flavour of {@code i} if {@code i} is not {@code null}, {@code defaultValue}
     * otherwise.
     *
     * @param i            the {@link Integer} instance to unbox.
     * @param defaultValue the default value provided if {@code i} is {@code null}.
     * @return the unboxed flavour of {@code i} if {@code i} is not {@code null}, {@code defaultValue}
     * otherwise.
     */
    public static int unboxInt(final Integer i, final int defaultValue) {
        return i == null ? defaultValue : i.intValue();
    }

    /**
     * Return the unboxed flavour of {@code l} if {@code l} is not {@code null}, {@code defaultValue}
     * otherwise.
     *
     * @param l            the {@link Long} instance to unbox.
     * @param defaultValue the default value provided if {@code l} is {@code null}.
     * @return the unboxed flavour of {@code l} if {@code l} is not {@code null}, {@code defaultValue}
     * otherwise.
     */
    public static long unboxLong(final Long l, final long defaultValue) {
        return l == null ? defaultValue : l.longValue();
    }

    /**
     * Return the unboxed flavour of {@code f} if {@code f} is not {@code null}, {@code defaultValue}
     * otherwise.
     *
     * @param f            the {@link Float} instance to unbox.
     * @param defaultValue the default value provided if {@code f} is {@code null}.
     * @return the unboxed flavour of {@code f} if {@code f} is not {@code null}, {@code defaultValue}
     * otherwise.
     */
    public static float unboxFloat(final Float f, final float defaultValue) {
        return f == null ? defaultValue : f.floatValue();
    }

    /**
     * Return the unboxed flavour of {@code d} if {@code d} is not {@code null}, {@code defaultValue}
     * otherwise.
     *
     * @param d            the {@link Double} instance to unbox.
     * @param defaultValue the default value provided if {@code d} is {@code null}.
     * @return the unboxed flavour of {@code d} if {@code d} is not {@code null}, {@code defaultValue}
     * otherwise.
     */
    public static double unboxDouble(final Double d, final double defaultValue) {
        return d == null ? defaultValue : d.doubleValue();
    }
}
