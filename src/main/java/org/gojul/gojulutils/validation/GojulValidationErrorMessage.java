package org.gojul.gojulutils.validation;

import java.io.Serializable;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Class {@code GojulValidationErrorMessage} is a handy object which stores an error message.
 * It is intended to be used alongside class {@link GojulValidationErrorMessageContainer} to
 * store error messages in a handy way. By the way this class allows easy decoupling between the
 * UI layer and the business layer of your application, as it does not rely on any UI framework.
 * </p>
 * <p>
 * If you wish to take benefit of the Jaxb serialization mechanism don't forget to annotate the parameter
 * classes with Jaxb annotations.
 * </p>
 * 
 * @author julien
 * 
 * @param <K> the key of the message, which identifies the element of the user interface that is
 * intended to be targeted by the error message. This object should be immutable.
 * @param <V> the message itself to display. This object should be immutable.
 */
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class GojulValidationErrorMessage<K extends Serializable, V extends Serializable> implements Serializable {

	private static final long serialVersionUID = 7703546180407879365L;

	@XmlElement
	private final K uiTarget;
	
	@XmlElement
	private final V message;
	
	/**
	 * Constructor.
	 * @param uiTarget the element to be targetted by this message in the UI.
	 * @param message the message to display.
	 * 
	 * @throws NullPointerException if any of the method parameters is {@code null}.
	 */
	public GojulValidationErrorMessage(final K uiTarget, final V message) {
		Objects.requireNonNull(uiTarget, "uiTarget is null");
		Objects.requireNonNull(message, "message is null");
		this.uiTarget = uiTarget;
		this.message = message;
	}
	
	/**
	 * Return the UI target element. This method is to be interpreted by the UI
	 * layer as the location where the error message should be displayed.
	 * @return the UI target element. 
	 */
	public K getUiTarget() {
		return this.uiTarget;
	}
	
	/**
	 * Return the message to display.
	 * @return the message to display.
	 */
	public V getMessage() {
		return this.message;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((uiTarget == null) ? 0 : uiTarget.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GojulValidationErrorMessage other = (GojulValidationErrorMessage) obj;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (uiTarget == null) {
			if (other.uiTarget != null)
				return false;
		} else if (!uiTarget.equals(other.uiTarget))
			return false;
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "GojulValidationErrorMessage [uiTarget=" + uiTarget + ", message=" + message + "]";
	}
	
	
}
