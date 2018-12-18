package org.gojul.gojulutils.validation;

import org.junit.Test;

public class GojulPreconditionsTest {
    
    @Test(expected = IllegalArgumentException.class)
    public void testCheckAssertionWithFalseAssertionLogsError() {
        GojulPreconditions.checkAssertion(1 == 0, "Assertion failed");
    }
    
    @Test
    public void testCheckAssertion() {
        GojulPreconditions.checkAssertion(1 > 0, "Assertion failed");
    }
    

}
