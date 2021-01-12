package org.gojul.gojulutils.validation;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GojulValidationErrorMessageTest {

    @Test(expected = NullPointerException.class)
    public void testConstructorWithNullKeyThrowsException() {
        new GojulValidationErrorMessage<String, String>(null, "message");
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorWithNullMessageThrowsException() {
        new GojulValidationErrorMessage<String, String>("uiTarget", null);
    }

    @Test
    public void testAll() {
        GojulValidationErrorMessage<String, String> msg = new GojulValidationErrorMessage<>("uiTarget", "message");

        assertEquals("uiTarget", msg.getUiTarget());
        assertEquals("message", msg.getMessage());
    }
}
