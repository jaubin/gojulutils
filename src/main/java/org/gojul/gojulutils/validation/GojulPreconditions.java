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
    
    
    private GojulPreconditions() {
        // Private constructor. Prevents class from
        // being instanciated from the outside.
    }
    
    /**
     * Check if the assertion {@code assertion} is successful. If this is not the case, log an error with
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
