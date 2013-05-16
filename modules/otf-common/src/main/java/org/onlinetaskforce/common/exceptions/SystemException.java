/*
* Copyright (c) 2010-2011 MVG. All Rights Reserved.
*/
package org.onlinetaskforce.common.exceptions;

/**
 * This class is initially created from a corresponding class of the HP Techstack.
 * SystemException uses a messageKey as its message.
 * It is the intention that messageKey is a key to a message in a resourceBundle.
 * SystemException can only be used for exceptions which can not be handled by the callers (non-recoverable).
 * <strong>ATTENTION:<strong> when adding a new subclass, do not forget this class in the "exceptionConfigurationMapper" bean,
 * defined in the "spring-webflow-config.xml".
 */
public class SystemException extends RuntimeException {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = -4345230519174130364L;

    /**
     * The arguments.
     */
    private Object[] arguments;

    /**
     * The message key.
     */
    private String messageKey;

    /**
     * The verzoekreferte.
     */
    private String verzoekreferte;

    /**
     * The vraagReferte.
     */
    private String vraagReferte;

    /**
     * The log referte.
     */
    private String logReferte;

    /**
     * @param cause The exception that is the underlying cause.
     */
    public SystemException(Throwable cause) {
        super(cause.getMessage(), cause);
    }

    /**
     * Default constructor.
     */
    public SystemException() {
        super();
    }

    /**
     * @param messageKey <b>Key>/b> to a message in a resourceBundle
     * @param cause      The exception that is the underlying cause.
     */
    public SystemException(String messageKey, Throwable cause) {
        super(messageKey, cause);
        this.messageKey = messageKey;
    }

    /**
     * Instantiates a new system exception.
     *
     * @param messageKey     the message key
     * @param cause          the cause
     * @param verzoekreferte the verzoekreferte
     * @param vraagReferte   the vraagReferte
     * @param logreferte     the logreferte
     */
    public SystemException(String messageKey, Throwable cause, String verzoekreferte, String vraagReferte, String logreferte) {
        super(messageKey, cause);
        this.messageKey = messageKey;
        this.setVerzoekreferte(verzoekreferte);
        this.setVraagReferte(vraagReferte);
        this.setLogReferte(logreferte);
    }

    /**
     * Instantiates a new system exception.
     *
     * @param messageKey     the message key
     * @param verzoekreferte the verzoekreferte
     * @param vraagReferte   the vraagReferte
     * @param logreferte     the logreferte
     */
    public SystemException(String messageKey, String verzoekreferte, String vraagReferte, String logreferte) {
        super(messageKey);
        this.messageKey = messageKey;
        this.setVerzoekreferte(verzoekreferte);
        this.setVraagReferte(vraagReferte);
        this.setLogReferte(logreferte);
    }

    /**
     * @param messageKey <b>Key>/b> to a message in a resourceBundle
     */
    public SystemException(String messageKey) {
        super(messageKey);
        this.messageKey = messageKey;
    }

    /**
     * @param messageKey <b>Key>/b> to a message in a resourceBundle
     * @param arguments  Arguments for the key
     */
    public SystemException(String messageKey, Object[] arguments) {
        super(messageKey);
        this.messageKey = messageKey;
        if(arguments != null) {
            this.arguments = arguments.clone();
        }
    }

    /**
     * @param messageKey <b>Key>/b> to a message in a resourceBundle
     * @param arguments  Arguments for the key
     * @param cause      The exception that is the underlying cause.
     */
    public SystemException(String messageKey, Throwable cause, Object[] arguments) {
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

    /**
     * Gets the verzoekreferte.
     *
     * @return the verzoekreferte
     */
    public String getVerzoekreferte() {
        return this.verzoekreferte;
    }

    /**
     * Sets the verzoekreferte.
     *
     * @param verzoekreferte the verzoekreferte
     */
    public final void setVerzoekreferte(String verzoekreferte) {
        this.verzoekreferte = verzoekreferte;
    }

    /**
     * Gets the vraagReferte.
     *
     * @return the vraagReferte
     */
    public String getVraagReferte() {
        return this.vraagReferte;
    }

    /**
     * Sets the vraagReferte.
     *
     * @param vraagReferte the vraagReferte
     */
    public final void setVraagReferte(String vraagReferte) {
        this.vraagReferte = vraagReferte;
    }

    /**
     * Gets the log referte.
     *
     * @return the log referte
     */
    public String getLogReferte() {
        return this.logReferte;
    }

    /**
     * Sets the log referte.
     *
     * @param logReferte the log referte
     */
    public final void setLogReferte(String logReferte) {
        this.logReferte = logReferte;
    }


}
