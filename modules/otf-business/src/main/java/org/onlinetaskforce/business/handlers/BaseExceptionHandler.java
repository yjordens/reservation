package org.onlinetaskforce.business.handlers;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.onlinetaskforce.common.exceptions.SystemException;
import org.onlinetaskforce.common.log.Log;
import org.onlinetaskforce.persistence.utils.OtfEnvironment;
import org.onlinetaskforce.persistence.utils.OtfSubsystem;
import org.onlinetaskforce.persistence.utils.SystemConstants;
import org.onlinetaskforce.persistence.utils.ThreadContextInfo;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;

/**
 * This class is initially based on a corresponding class of the STL project.
 * The base class for exception handlers of the otf project. Different endpoints (ex. Web frontend, Webservices)
 * can have different implementations.
 * Note that this exception handler is also a singleton can also be used directly for handling background exceptions that
 * should result in a notification of the application manager.
 * @author jordens
 * @since 15/03/13
 */
public class BaseExceptionHandler {

    /**
     * The one singleton instance.
     */
    private static BaseExceptionHandler baseExceptionHandler;
    /**
     * The current build number of the application.
     */
    private String applicationBuildNbr;
    //private MailService mailService;

    /**
     * Singleton method.
     * @return The one singleton instance.
     */
    public static synchronized BaseExceptionHandler getInstance() {
        if (baseExceptionHandler == null) {
            baseExceptionHandler = new BaseExceptionHandler();
        }
        return baseExceptionHandler;
    }

    /**
     * Protected constructor so that a specific implmentation is allowed.
     */
    protected BaseExceptionHandler() {
    }

    /**
     * Handles the given Throwable.
     * @param t The Trhowable to handle.
     * @return The generated unique id of the Throwable.
     */
    public String handle(Throwable t) {
        return handle(null, t);
    }

    /**
     * Handles the given Throwable.
     * @param msg An errormessage with context information about the given Throwable.
     * @param t The Trhowable to handle.
     * @return The generated unique id of the Throwable.
     */
    public String handle(String msg, Throwable t) {
        String exceptionID = generateUniqueId();
        String errLogMsg = buildErrorMessage(exceptionID, msg, t);
        Log.error(this, errLogMsg);
        return exceptionID;
    }

    /**
     * Handles a background Throwable. This exception handler will log the error message and will send a notification mail to an error mailbox.
     * @param t The Trhowable to handle.
     * @return The generated unique id of the Throwable.
     */
    public String handleBackgroundException(Throwable t) {
        return handleBackgroundException(null, t);
    }

    /**
     * Handles a background Throwable. This exception handler will log the error message and will send a notification mail to an error mailbox.
     * @param msg An errormessage with context information about the given Throwable.
     * @param t The Trhowable to handle.
     * @return The generated unique id of the Throwable.
     */
    public String handleBackgroundException(String msg, Throwable t) {
        String exceptionID = generateUniqueId();
        String subject = buildSubject(msg, t, true);
        final String errorMessage = buildErrorMessage(exceptionID, msg, t);
        Log.error(this, errorMessage);
//        if (!SystemConstants.DEVELOPMENT) {
//            mailService.sendMailToErrorMailbox(subject, errorMessage);
//        }
        return exceptionID;
    }

    /**
     * Builds the subject of the notification mail based on the given parameters.
     * @param t See description.
     * @return See description.
     */
    protected String buildSubject(Throwable t) {
        return buildSubject(null, t, false);
    }


    /**
     * Builds the subject of the notification mail based on the given parameters.
     * @param msg See description.
     * @param t See description.
     * @param appendThrowableMessage If <code>true</code>, the message of the root cause of the given Throwable will be appended to the given message.
     * @return See description.
     */
    protected String buildSubject(String msg, Throwable t, boolean appendThrowableMessage) {
        int subjectlength = 150;
        String subject = "Error";
        Throwable rootCause = null;
        if (t != null) {
            rootCause = getRootCause(t);
            if (rootCause == null) {
                rootCause = t;
            }
        }
        if (msg != null) {
            subject = msg;
            if (appendThrowableMessage && rootCause != null && rootCause.getMessage() != null) {
                subject = subject + ": " + rootCause.getMessage();
            }
            StringUtils.abbreviate(subject, subjectlength);
        } else if (rootCause != null && rootCause.getMessage() != null) {
            subject = rootCause.getMessage();
            StringUtils.abbreviate(subject, subjectlength);
        }
        return subject;
    }

    protected String buildErrorMessage(String exceptionID, Throwable t) {
        return buildErrorMessage(exceptionID, null, t);
    }

    protected String buildErrorMessage(String exceptionID, String msg, Throwable t) {
        StringBuilder errLogMsg = new StringBuilder(2048);
        errLogMsg.append("\n");
        errLogMsg.append("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -\n");
        errLogMsg.append("Exception occured in the otf application");
        if (exceptionID != null) {
            errLogMsg.append(" (ID = ").append(exceptionID).append(")\n");
        } else {
            errLogMsg.append("\n");
        }
        errLogMsg.append("Build: ");
        errLogMsg.append(applicationBuildNbr == null ? "undefined" : applicationBuildNbr);
        errLogMsg.append("\n");
        errLogMsg.append(ThreadContextInfo.getInstance().toString());
        errLogMsg.append("\n");
        errLogMsg.append("- - - - - - - - - - - - - - - - BEGIN - - - - - - - - - - - - - - - - - -\n");
        errLogMsg.append("\n[Messages]\n");
        StringBuilder errorMessages = new StringBuilder(512);
        if (msg != null) {
            errorMessages.append(msg);
        }
        buildErrorMessages(t, errorMessages);
        if (errorMessages.length() > 0) {
            errLogMsg.append(errorMessages);
            errLogMsg.append("\n");
        } else {
            errLogMsg.append("(no messages available)\n");
        }
        errLogMsg.append("\n[Stacktrace root exception]\n");
        appendRootStackTrace(t, errLogMsg);
        errLogMsg.append("- - - - - - - - - - - - - - - - END - - - - - - - - - - - - - - - - - -\n");
        return errLogMsg.toString();
    }


    protected String generateUniqueId() {
        String environment = System.getProperty(SystemConstants.SYSTEM_PROPERTY_KEY_ENVIRONMENT, "empty environment");
        String subsystem = System.getProperty(SystemConstants.SYSTEM_PROPERTY_KEY_SUBSYSTEM, "empty subsystem");
        StringBuilder result = new StringBuilder();
        result.append(Math.abs(environment.hashCode() % 10000));
        result.append(Math.abs(subsystem.hashCode() % 10000));
        result.append(Long.toString(System.currentTimeMillis()));
        return result.toString();
    }

    protected void buildErrorMessages(Throwable t, StringBuilder errorMessages) {
        Throwable cause = null;
        if (t != null) {
            String msg = t.getMessage(); // get message from this exception
            if (msg == null) {
                msg = "(no message available)";
            }
            if (errorMessages.length() > 0) {
                errorMessages.append("\n");
            }
            errorMessages.append(getExceptionName(t));
            errorMessages.append(": ");
            errorMessages.append(msg);
            errorMessages.append("\n");

            cause = t.getCause();
        }
        boolean root = (cause == null); // check cause to see if we are in root exception
        if (root) {
            return; // don't add message of root cause (already present in stacktrace)
        }
        buildErrorMessages(cause, errorMessages);
    }

    protected String getExceptionName(Throwable t) {
        try {
            String exceptionName = t.getClass().getName();
            int index = exceptionName.lastIndexOf('.');
            if (index != -1) {
                exceptionName = exceptionName.substring(index + 1, exceptionName.length());
            }
            return exceptionName;
        } catch (Throwable e) { // NOSONAR
            return "Exception";
        }
    }

    /**
     * Builds the String representation of the given Throwable.
     * @param t See description.
     * @return See description, an empty String when the given Throwable was <code>null</code>.
     */
    public String buildPrintedStackTrace(Throwable t) {
        if (t == null) {
            return "";
        }
        StringWriter sw = new StringWriter(2048);
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
    }


    protected Throwable getRootCause(Throwable t) {
        Throwable rootCause = ExceptionUtils.getRootCause(t);
        if (rootCause == null) {
            return t;
        }
        return rootCause;
    }

    protected Throwable getParentOfRootCause(Throwable t) {
        Throwable cause = null;
        if (t != null) {
            cause = t.getCause();
        }
        if (cause == null) {
            return null; // this is the root cause -> no parent available
        }
        Throwable subsCause = cause.getCause();
        if (subsCause == null) {
            return t; // this the parent of the root cause
        }
        return getParentOfRootCause(cause);
    }

    protected void appendRootStackTrace(Throwable t, StringBuilder errLogMsg) {
        Throwable rootCause = getRootCause(t);
        String printedStackTrace = buildPrintedStackTrace(rootCause);
        if (printedStackTrace.length() > 0) {
            errLogMsg.append(printedStackTrace);
            errLogMsg.append("\n");
            if (printedStackTrace.length() < 100) {
                errLogMsg.append("\n[Abnormal short root stacktrace -> stacktrace of first parent]\n");
                Throwable parent = getParentOfRootCause(t);
                if (parent != null) {
                    String backupTrace = buildPrintedStackTrace(parent);
                    errLogMsg.append(backupTrace);
                    errLogMsg.append("\n");
                } else {
                    errLogMsg.append("(no parent exception available)\n");
                }
            }
        } else {
            errLogMsg.append("(no stacktrace available)\n");
        }
    }

    public void setApplicationBuildNbr(String nbr) {
        applicationBuildNbr = nbr;
    }

    public String getApplicationBuildNbr() {
        return applicationBuildNbr;
    }

    // Dependencies
//    public void setMailService(MailService mailService) {
//        this.mailService = mailService;
//    }



    // Static helper method
    protected static String resolveUniqueId(String uniqueId) {
        if (uniqueId == null) {
            return null;
        }
        if (uniqueId.length() < 9) {
            return "Not able to resolve the unique id, the length was to short.";
        }
        String envHashcode = uniqueId.substring(0, 4);
        String subsysHashcode = uniqueId.substring(4, 8);
        String timeInMillis = uniqueId.substring(8);
        Log.debug(BaseExceptionHandler.class, "envHashcode = " + envHashcode);
        Log.debug(BaseExceptionHandler.class, "subsysHashcode = " + subsysHashcode);
        Log.debug(BaseExceptionHandler.class, "timeInMillis = " + timeInMillis);

        String environment = "not resolvable";
        for (String env : OtfEnvironment.retrieveEnvironmentValues()) {
            Log.debug(BaseExceptionHandler.class, "checking environment " + env);
            Log.debug(BaseExceptionHandler.class, "hashcode of environment " + Math.abs(env.hashCode()  % 10000));
            if (Math.abs(env.hashCode()  % 10000) == Integer.valueOf(envHashcode)) {
                environment = env;
            }
        }
        String subsystem = "not resolvable";
        for (String subs : OtfSubsystem.retrieveSubsystemValues()) {
            Log.debug(BaseExceptionHandler.class, "checking subsystem " + subs);
            Log.debug(BaseExceptionHandler.class, "hashcode of subsystem " + Math.abs(subs.hashCode()  % 10000));
            if (Math.abs(subs.hashCode()  % 10000) == Integer.valueOf(envHashcode)) {
                subsystem = subs;
            }
        }

        Date timestamp = new Date(new Long(timeInMillis));

        return "Environment: " + environment + ", subsystem: "  + subsystem + ", timestamp: " + timestamp;
    }

    /**
     * Returns the first exception of the given class from the given exception chain.
     * @param exceptionType See description.
     * @param startOfChain See description.
     * @return See description.
     */
    public Throwable getNestedExceptionOfType(Class exceptionType, Throwable startOfChain) {
        List throwables = ExceptionUtils.getThrowableList(startOfChain);
        for (Object throwable : throwables) {
            Throwable t = (Throwable) throwable;
            if (exceptionType.isAssignableFrom(t.getClass())) {
                return t;
            }
        }
        return null;
    }

    /**
     * If the given exception is a SystemException, throws this exception. Otherwise throws the given exception wrapped in a SystemException.
     * @param e See description.
     */
    public void throwAsSystemException(Exception e) {
        if (e instanceof SystemException) {
            throw (SystemException) e;
        } else {
            throw new SystemException(e);
        }
    }


    /**
     * Main method that can be used to resolve a unique id.
     * @param args Not used.
     */
    public static void main(String[] args) {
        String resolvedId = BaseExceptionHandler.resolveUniqueId("397739771321887064922");
        Log.info(BaseExceptionHandler.class, "resolved id = " + resolvedId);
        // System.out.println("resolved id = " + resolvedId);
    }

}
