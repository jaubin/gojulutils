package org.gojul.gojulutils.validation;

import java.util.Objects;

/**
 * Exception {@code GojulValidationException} is thrown when a {@link GojulValidationErrorMessageContainer}
 * instance contains error. Such an exception allows easily catch when used with an UI or web services.
 * 
 * @author julien
 *
 */
public class GojulValidationException extends RuntimeException {

    private static final long serialVersionUID = 2772365102392642788L;

    private final GojulValidationErrorMessageContainer<?, ?> errorMessageContainer;
    
    /**
     * Constructor.
     * @param msgContainer the error message container used.
     * 
     * @throws NullPointerException if {@code msgContainer} is {@code null}.
     */
    public GojulValidationException(final GojulValidationErrorMessageContainer<?, ?> msgContainer) {
        Objects.requireNonNull(msgContainer, "msgContainer is null");
        this.errorMessageContainer = msgContainer;
    }
    
    /**
     * Constructor.
     * @param msg the exception message.
     * 
     * @param msgContainer the error message container used.
     * 
     * @throws NullPointerException if {@code msgContainer} is {@code null}.
     */
    public GojulValidationException(final String msg, final GojulValidationErrorMessageContainer<?, ?> msgContainer) {
        super(msg);
        Objects.requireNonNull(msgContainer, "msgContainer is null");
        this.errorMessageContainer = msgContainer;
    }
    
    /**
     * Return the error message container contained in this instance.
     * @return the error message container contained in this instance.
     */
    public GojulValidationErrorMessageContainer<?, ?> getErrorMessageContainer() {
        return this.errorMessageContainer;
    }
    
    /**
     * Check the error message container {@code errorMsgContainer}. If it has errors, raise an exception. This
     * method is equivalent to :
     * <pre>
     * <code>
     * if (errorMsgContainer.hasErrors) {
     *    throw new GojulValidationException("Validation failed", errorMsgContainer);
     * }
     * </code>
     * </pre>
     * However it makes code more readable by avoiding the {@code if} block.
     * 
     * @param errorMsgContainer the error message container to validate.
     * 
     * @throws NullPointerException if {@code errorMsgContainer} is {@code null}.
     */
    public static void checkMessageContainerForErrors(final GojulValidationErrorMessageContainer<?, ?> errorMsgContainer) {
        Objects.requireNonNull(errorMsgContainer, "errorMsgContainer is null");
        if (errorMsgContainer.hasErrors()) {
            throw new GojulValidationException("Validation failed", errorMsgContainer);
        }
    }
}
