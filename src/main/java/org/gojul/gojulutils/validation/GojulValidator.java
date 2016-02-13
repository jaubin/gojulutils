package org.gojul.gojulutils.validation;

import java.io.Serializable;

/**
 * <p>
 * Interface {@code GojulValidator} is a generic-purpose interface which allows you to easily validate
 * your business rules. When validation fails, just populate the message container provided.
 * </p>
 * <p>
 * The goal here is to split your validation rules in many small easy-to-test rule validators so that you
 * improve code reusability while having easy to test and maintain code. Then you can assemble your validators
 * using class {@link GojulValidatorComposite}. Actually these validation rules should at best have a package-private
 * visibility and then you assemble them using the composite.
 * </p>
 * <p>
 * If you use Spring framework to do the assembly of validation rules you just need to declare all your validators
 * in a configuration class (or even in XML) and then referencing them in a bean list.
 * </p>
 * <p>
 * This class is an alternative to JSR-303 validation. The latter is very efficient but not handy when it comes to integrate
 * your validation objects with database, and perhaps not really efficient. This one is very easy to integrate with frameworks
 * like Spring.
 * </p>
 * 
 * @author julien
 *
 * @param <E> the type of elements to validate.
 * @param <K> the key of the message, which identifies the element of the user interface that is
 * intended to be targeted by the error message. This object should be immutable.
 * @param <V> the message itself to display. This object should be immutable.
 * 
 * @see org.gojul.gojulutils.validation.GojulValidatorComposite
 * @see org.gojul.gojulutils.validation.GojulValidationErrorMessage
 */
public interface GojulValidator<E, K extends Serializable, V extends Serializable> {
	
	/**
	 * Validate the element {@code element}. If {@code element} does not satisfy the business rule then log an
	 * error into {@code errorMsgContainer}.
	 * @param element the element to validate.
	 * @param errorMsgContainer the error message container used to store error messages.
	 */
	public void validate(final E element, final GojulValidationErrorMessageContainer<K, V> errorMsgContainer);
	
}
