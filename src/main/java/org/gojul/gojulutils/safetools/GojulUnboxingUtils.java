package org.gojul.gojulutils.safetools;

/**
 * While autoboxing is a safe process, this is not the case of unboxing. The goal of this
 * class is to make unboxing safe by performing null checks and returning default value
 * when necessary.
 * 
 * @author julien
 *
 */
public class GojulUnboxingUtils {
	
	/**
	 * Private constructor. Prevents the class from being instanciated.
	 */
	private GojulUnboxingUtils() {
		
	}
	
	/**
	 * Return {@code true} if {@code b} equals {@link Boolean#TRUE}, {@code false} otherwise.
	 * @param b the object to unbox.
	 * @return {@code true} if {@code b} equals {@link Boolean#TRUE}, {@code false} otherwise.
	 */
	public static boolean safeUnboxBoolean(final Boolean b) {
		return Boolean.TRUE.equals(b);
	}
	
	/**
	 * Return the unboxed flavour of {@code c} if {@code c} is not {@code null}, {@code defaultValue}
	 * otherwise.
	 * @param c the {@link Character} instance to unbox.
	 * @param defaultValue the default value provided if {@code c} is {@code null}.
	 * @return the unboxed flavour of {@code c} if {@code c} is not {@code null}, {@code defaultValue}
	 * otherwise.
	 */
	public static char safeUnboxChar(final Character c, final char defaultValue) {
		return c == null ? defaultValue: c.charValue();
	}

}
