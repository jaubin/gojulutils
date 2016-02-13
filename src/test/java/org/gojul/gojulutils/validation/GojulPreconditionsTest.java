package org.gojul.gojulutils.validation;

import org.junit.Test;

public class GojulPreconditionsTest {

	@Test(expected = NullPointerException.class)
	public void testCheckNotNullWithNullValueLogsError() {
		GojulPreconditions.checkNotNull(null, "null detected");
	}
	
	@Test
	public void testCheckNotNull() {
		GojulPreconditions.checkNotNull("notNull", "null detected");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCheckAssertionWithFalseAssertionLogsError() {
		GojulPreconditions.checkAssertion(1 == 0, "Assertion failed");
	}
	
	@Test
	public void testCheckAssertion() {
		GojulPreconditions.checkAssertion(1 > 0, "Assertion failed");
	}
	

}
