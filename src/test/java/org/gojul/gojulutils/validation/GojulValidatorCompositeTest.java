package org.gojul.gojulutils.validation;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class GojulValidatorCompositeTest {

    private GojulValidatorComposite<Date, String, String> composite;
    private GojulValidator<Date, String, String> v1;
    private GojulValidator<Date, String, String> v2;


    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        v1 = mock(GojulValidator.class);
        v2 = mock(GojulValidator.class);
        composite = new GojulValidatorComposite<>(Arrays.asList(v1, v2));
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorWithNullListThrowsException() {
        new GojulValidatorComposite<>(null);
    }

    @Test(expected = NullPointerException.class)
    public void testValidateWithNullElementThrowsException() {
        composite.validate(null, new GojulValidationErrorMessageContainer<String, String>());
    }

    @Test(expected = NullPointerException.class)
    public void testValidateWithNullMessageContainerThrowsException() {
        composite.validate(new Date(), null);
    }

    @Test
    public void testValidate() {
        GojulValidationErrorMessageContainer<String, String> errorMsgContainer = new GojulValidationErrorMessageContainer<>();
        Date d = new Date();

        composite.validate(d, errorMsgContainer);

        verify(v1).validate(d, errorMsgContainer);
        verify(v2).validate(d, errorMsgContainer);
    }

}
