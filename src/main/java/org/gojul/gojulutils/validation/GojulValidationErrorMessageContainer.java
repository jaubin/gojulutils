package org.gojul.gojulutils.validation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p>
 * Class {@code GojulValidationErrorMessageContainer} contains instances of {@link GojulValidationErrorMessage}.
 * The goal of this class is to avoid error checking idioms like the following one :
 * <pre>
 * <code>
 * if (conditionFailed) {
 *    addError()
 * }
 * </code>
 * </pre>
 * </p>
 * <p>
 * Using this class you can directly perform your assertions and add errors in a declarative way, which makes your
 * code much more readable and much easier to test.
 * </p>
 * 
 * @author julien
 *
 * @param <K> the key of the message, which identifies the element of the user interface that is
 * intended to be targeted by the error message. This object should be immutable.
 * @param <V> the message itself to display. This object should be immutable.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class GojulValidationErrorMessageContainer<K extends Serializable, V extends Serializable> implements Serializable {

	private static final long serialVersionUID = -3709275791472687630L;
	
	/**
	 * Interface {@code GojulValidationErrorMessageInstanciator} is a functional interface which allows you
	 * to instanciate error messages easily. It is very handy to use with Java 8 lambdas, but can be painful
	 * to use with pre-Java 8 classes.
	 * 
	 * @author julien
	 *
	 * @param <K> the key of the message, which identifies the element of the user interface that is
	 * intended to be targeted by the error message. This object should be immutable.
	 * @param <V> the message itself to display. This object should be immutable.
	 */
	public static interface GojulValidationErrorMessageInstanciator<K extends Serializable, V extends Serializable> {
		
		/**
		 * Create a new error message.
		 * @return a new error message.
		 */
		public GojulValidationErrorMessage<K, V> instanciateErrorMessage();
	}
	
	@XmlElement
	private List<GojulValidationErrorMessage<K, V>> errorMessages;
	
	/**
	 * Constructor.
	 */
	public GojulValidationErrorMessageContainer() {
		this.errorMessages = new ArrayList<>();
	}
	
	/**
	 * Add the error message {@code msg} to this container. This method is useful when you wish 
	 * to add an error message for a condition which is already known as failed.
	 * @param msg the message to add.
	 * 
	 * @throws NullPointerException if {@code msg} is {@code null}.
	 */
	public void addError(final GojulValidationErrorMessage<K, V> msg) {
		GojulPreconditions.checkNotNull(msg, "msg is null");
		this.errorMessages.add(msg);
	}
	
	/**
	 * Add the error message {@code msg} to this container if and only if {@code assertion} is {@code false}.
	 * This method is useful for pre Java-8 as they do not support lambda. However it is quite inefficient
	 * as an instance of {@code msg} is created even if {@code assertion} is {@code true}.
	 * @param assertion the assertion to test.
	 * @param msg the message to add if {@code assertion} is {@code false}.
	 * 
	 * @throws NullPointerException if {@code msg} is {@code false}.
	 * 
	 * @see GojulValidationErrorMessageContainer#addError(boolean, GojulValidationErrorMessageInstanciator)
	 */
	public void addError(final boolean assertion, final GojulValidationErrorMessage<K, V> msg) {
		GojulPreconditions.checkNotNull(msg, "msg is null");
		if (!assertion) {
			this.errorMessages.add(msg);
		}
	}
	
	/**
	 * Instanciate an error message using {@code instanciator} and add it to this container if and only if {@code assertion}
	 * is {@code false}. This method is very handy to use with Java 8 as it supports lambdas.
	 * 
	 * @param assertion the assertion to test.
	 * @param instanciator the message instanciator used if {@code assertion} is {@code false}.
	 * 
	 * @throws NullPointerException if {@code instanciator} is {@code false}.
	 * @throws IllegalArgumentException if {@code instanciator} returns a {@code null} value.
	 */
	public void addError(final boolean assertion, final GojulValidationErrorMessageInstanciator<K, V> instanciator) {
		GojulPreconditions.checkNotNull(instanciator, "instanciator is null");
		if (!assertion) {
			GojulValidationErrorMessage<K, V> msg = instanciator.instanciateErrorMessage();
			GojulPreconditions.checkAssertion(msg != null, "Trying to create a null error message !");
			this.errorMessages.add(msg);
		}
	}
	
	/**
	 * Return the list of error messages.
	 * @return the list of error messages.
	 */
	public List<GojulValidationErrorMessage<K, V>> getMessages() {
		return Collections.unmodifiableList(errorMessages);
	}
	
	/**
	 * Return {@code true} if this container contains error, {@code false} otherwise.
	 * @return {@code true} if this container contains error, {@code false} otherwise.
	 */
	public boolean hasErrors() {
		return !errorMessages.isEmpty();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "GojulValidationErrorMessageContainer [errorMessages=" + errorMessages + "]";
	}
	
	
}
