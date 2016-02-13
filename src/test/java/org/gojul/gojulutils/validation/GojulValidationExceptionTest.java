package org.gojul.gojulutils.validation;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class GojulValidationExceptionTest {

	private GojulValidationErrorMessageContainer<String, String> msgContainer;
	
	@Before
	public void setUp() throws Exception {
		msgContainer = new GojulValidationErrorMessageContainer<>();
	}

	@Test(expected = NullPointerException.class)
	public void testConstructorWithoutMessageAndNullContainerThrowsException() {
		new GojulValidationException(null);
	}
	
	@Test
	public void testConstructorWithoutMessage() {
		GojulValidationException e = new GojulValidationException(msgContainer);
		assertEquals(msgContainer, e.getErrorMessageContainer());
	}

	@Test(expected = NullPointerException.class)
	public void testConstructorWithMessageAndNullContainerThrowsException() {
		new GojulValidationException("message", null);
	}
	
	@Test
	public void testConstructorWithMessage() {
		GojulValidationException e = new GojulValidationException("message", msgContainer);
		assertEquals(msgContainer, e.getErrorMessageContainer());
	}

	@Test(expected = NullPointerException.class)
	public void testCheckMessageContainerForErrorsWithNullMessageContainerThrowsException() {
		GojulValidationException.checkMessageContainerForErrors(null);
	}
	
	@Test(expected = GojulValidationException.class)
	public void testCheckMessageContainerForErrorsWithContainerWithErrorsThrowsException() {
		msgContainer.addError(new GojulValidationErrorMessage<>("key", "message"));
		GojulValidationException.checkMessageContainerForErrors(msgContainer);
	}
	
	@Test
	public void testCheckMessageContainerForErrors() {
		GojulValidationException.checkMessageContainerForErrors(msgContainer);
	}
}
