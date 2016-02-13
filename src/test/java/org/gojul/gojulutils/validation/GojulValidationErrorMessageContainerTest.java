package org.gojul.gojulutils.validation;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class GojulValidationErrorMessageContainerTest {

	private GojulValidationErrorMessageContainer<String, String> msgContainer;
	private GojulValidationErrorMessage<String, String> msg1;
	private GojulValidationErrorMessage<String, String> msg2;
	
	@Before
	public void setup() {
		msgContainer = new GojulValidationErrorMessageContainer<>();
		msg1 = new GojulValidationErrorMessage<>("key1", "msg1");
		msg2 = new GojulValidationErrorMessage<>("key2", "msg2");
	}
	
	@Test(expected = NullPointerException.class)
	public void testAddErrorWithSingleMessageAndNullMessageThrowsException() {
		msgContainer.addError(null);
	}
	
	@Test
	public void testAddErrorWithSingleMessage() {
		msgContainer.addError(msg1);
		msgContainer.addError(msg2);
		
		assertEquals(Arrays.asList(msg1, msg2), msgContainer.getMessages());
	}
	
	@Test(expected = NullPointerException.class)
	public void testAddErrorWithAssertionAndNullMessageThrowsException() {
		msgContainer.addError(1 == 0, null);
	}
	
	@Test
	public void testAddErrorWithAssertion() {
		msgContainer.addError(1 == 0, msg1);
		msgContainer.addError(1 > 0, msg2);
		
		assertEquals(Arrays.asList(msg1), msgContainer.getMessages());
	}

}
