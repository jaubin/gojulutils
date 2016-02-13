package org.gojul.gojulutils.validation;

import static org.junit.Assert.*;
import static org.gojul.gojulutils.validation.GojulValidationErrorMessageContainer.*;

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
		msgContainer.addError(1 == 0, (GojulValidationErrorMessage<String, String>) null);
	}
	
	@Test
	public void testAddErrorWithAssertion() {
		msgContainer.addError(1 == 0, msg1);
		msgContainer.addError(1 > 0, msg2);
		
		assertEquals(Arrays.asList(msg1), msgContainer.getMessages());
	}
	
	@Test(expected = NullPointerException.class)
	public void testAddErrorWithNullInstanciatorThrowsException() {
		msgContainer.addError(1 == 0, (GojulValidationErrorMessageInstanciator<String, String>) null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAddErrorWithInstanciatorReturningNullThrowsException() {
		msgContainer.addError(1 == 0, new GojulValidationErrorMessageInstanciator<String, String>() {
			@Override
			public GojulValidationErrorMessage<String, String> instanciateErrorMessage() {
				return null;
			}
		});
	}
	
	@Test
	public void testAddError() {
		msgContainer.addError(1 == 0, new GojulValidationErrorMessageInstanciator<String, String>() {
			@Override
			public GojulValidationErrorMessage<String, String> instanciateErrorMessage() {
				return msg1;
			}
		});
		msgContainer.addError(1 > 0, new GojulValidationErrorMessageInstanciator<String, String>() {
			@Override
			public GojulValidationErrorMessage<String, String> instanciateErrorMessage() {
				return msg2;
			}
		});
		
		assertEquals(Arrays.asList(msg1), msgContainer.getMessages());
	}
	
	@Test
	public void testHasErrorsWithEmptyContainerReturnsFalse() {
		assertFalse(msgContainer.hasErrors());
	}
	
	@Test
	public void testHasErrors() {
		msgContainer.addError(msg1);
		assertTrue(msgContainer.hasErrors());
	}

}
