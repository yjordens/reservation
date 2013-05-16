package org.onlinetaskforce.common.exceptions;

/**
 * This class is initially created from a corresponding class of the HP Techstack.
 * BusinessException uses a messageKey as its message.
 * It is the intention that messageKey is a key to a message in a resourceBundle.
 * A BusinessException:
 * <ul>
 *  <li>Can only be used for exceptions which can be handled by the callers (recoverable).</li>
 *  <li>Is a Superclass, which all possible Recoverable Application exceptions should subclass, or can be used directly.</li>
 * </ul>
 * <strong>ATTENTION:<strong> when adding a new subclass, do not forget this class in the "exceptionConfigurationMapper" bean,
 * defined in the "spring-webflow-config.xml".
 */
public class BusinessException extends Exception {

    private static final long serialVersionUID = -5118546668268017412L;

    private Object[] arguments;
    private String messageKey;
    
    /**
     * @param messageKey <b>Key>/b> to a message in a resourceBundle
     * @param cause      The exception that is the underlying cause.
     */
    public BusinessException(String messageKey, Throwable cause) {
        super(messageKey, cause);
        this.messageKey = messageKey;
    }

    /**
     * @param messageKey <b>Key>/b> to a message in a resourceBundle
     */
    public BusinessException(String messageKey) {
        super(messageKey);
        this.messageKey = messageKey;
    }

    /**
     * @param messageKey <b>Key>/b> to a message in a resourceBundle
     * @param arguments     Arguments for the key
     */
    public BusinessException(String messageKey, Object... arguments) {
        super(messageKey);
        this.messageKey = messageKey;
        if(arguments != null) {
            this.arguments = arguments.clone();
        }
    }

    /**
     * @param messageKey <b>Key>/b> to a message in a resourceBundle
     * @param arguments     Arguments for the key
     * @param cause      The exception that is the underlying cause.
     */
    public BusinessException(String messageKey, Throwable cause, Object[] arguments) {
        super(messageKey, cause);
        this.messageKey = messageKey;
        if(arguments != null) {
            this.arguments = arguments.clone();
        }
    }
    /**
     * Returns the parameters for the message key
     *
     * @return see description
     */
    public Object[] getArguments() {
        return arguments;
    }

    /**
     * Getter for property 'messageKey'.
     *
     * @return Value for property 'messageKey'.
     */
    public String getMessageKey() {
        return messageKey;
    }
}

