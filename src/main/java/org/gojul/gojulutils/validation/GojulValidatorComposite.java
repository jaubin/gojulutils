package org.gojul.gojulutils.validation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class {@code GojulValidatorComposite} is an implementation of the GoF composite pattern which
 * makes it possible to assemble validation rules implemented by {@link GojulValidator} instances
 * very easily.
 * 
 * @author julien
 *
 * @param <E> the type of elements to validate.
 * @param <K> the key of the message, which identifies the element of the user interface that is
 * intended to be targeted by the error message. This object should be immutable.
 * @param <V> the message itself to display. This object should be immutable.
 */
public class GojulValidatorComposite<E, K extends Serializable, V extends Serializable> 
	implements GojulValidator<E, K, V> {

	private final List<GojulValidator<E, K, V>> validators;
	
	/**
	 * Constructor.
	 * @param validators the validators used by this object.
	 * 
	 * @throws NullPointerException if {@code validators} is {@code null}.
	 */
	public GojulValidatorComposite(final List<GojulValidator<E, K, V>> validators) {
		Objects.requireNonNull(validators, "validators is null");
		this.validators = new ArrayList<>(validators);
	}
	
	/**
	 * Perform the actual validation. Note that unlike what is specified in the GojulValidator interface
	 * this method performs nullity checks on parameters.
	 * 
	 * @param element the element to validate.
	 * @param errorMsgContainer the error message container to populate.
	 * 
	 * @see org.gojul.gojulutils.validation.GojulValidator#validate(Object, GojulValidationErrorMessageContainer)
	 */
	@Override
	public void validate(E element, GojulValidationErrorMessageContainer<K, V> errorMsgContainer) {
		Objects.requireNonNull(element, "element is null");
		Objects.requireNonNull(errorMsgContainer, "errorMsgContainer is null");
		
		for (GojulValidator<E, K, V> validator: validators) {
			validator.validate(element, errorMsgContainer);
		}
	}

}
