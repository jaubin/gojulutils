package org.gojul.gojulutils.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class {@code GojulPreconditions} contains various assertions that can be used anywhere
 * in your application. It is very similar to Guava's Preconditions class, but having this class
 * here avoids the necessity to import the whole Guava library just for these Preconditions.
 * 
 * @author julien
 *
 */
public class GojulPreconditions {

	private final static Logger log = LoggerFactory.getLogger(GojulPreconditions.class);
	
	/**
	 * Private constructor. Prevents class from
	 * being instanciated from the outside.
	 */
	private GojulPreconditions() {
		
	}
	
	/**
	 * Test if {@code obj} is null. If this is the case, log an error with message {@code message}
	 * and raise a {@link NullPointerException}.
	 * 
	 * @param obj the {@link Object} to test.
	 * @param message the message to display if {@code obj} is {@code null}.
	 * 
	 * @throws NullPointerException if {@code obj} is {@code null}.
	 */
	public static void checkNotNull(final Object obj, final String message) {
		if (obj == null) {
			log.error(message);
			throw new NullPointerException(message);
		}
	}
	
	/**
	 * Test if the assertion {@code assertion} is successful. If this is not the case, log an error with
	 * message {@code message} and raise a {@link IllegalArgumentException}. This is very similar to the
	 * assert keyword from Java except that it does not need to be enabled from the Java command line.
	 * 
	 * @param assertion the assertion to test.
	 * @param message the message to log if {@code assertion} is {@code false}.
	 * 
	 * @throws IllegalArgumentException if {@code assertion} is {@code false}.
	 */
	public static void checkAssertion(final boolean assertion, final String message) {
		if (!assertion) {
			log.error(message);
			throw new IllegalArgumentException(message);
		}
	}
}
