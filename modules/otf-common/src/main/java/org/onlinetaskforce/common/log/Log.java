/*
 * Copyright (c) 2010-2011 MVG. All Rights Reserved.
 */
package org.onlinetaskforce.common.log;

import org.apache.log4j.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;


/**
 * The Class Log.
 *
 * @author $Author: jordens.
 * @version $LastChangedRevision: 2281 $, $LastChangedDate: 2010-10-20 10:45:00 +0200 (wo, 20 okt 2010) $.
 */
public class Log {

    /**
     * Returns if is info enabled.
     *
     * @param locus The name of the logger to retrieve.
     * @return true, if is info enabled
     */
    public static boolean isDebugEnabled(String locus) {
        Logger l = Logger.getLogger(locus);
        return l.isDebugEnabled();
    }

    /**
     * Returns if is info enabled.
     *
     * @param locusObject The object whose classname is used as name of the logger.
     * @return true, if is info enabled
     */
    public static boolean isDebugEnabled(Object locusObject) {
        Logger l = Logger.getLogger(locusObject.getClass());
        return l.isDebugEnabled();
    }

    /**
     * Returns if is debug enabled.
     *
     * @param locus The name of the logger to retrieve.
     * @return true, if is debug enabled
     */
    public static boolean isInfoEnabled(String locus) {
        Logger l = Logger.getLogger(locus);
        return l.isInfoEnabled();
    }

    /**
     * Returns if is debug enabled.
     *
     * @param locusObject The object whose classname is used as name of the logger.
     * @return true, if is debug enabled
     */
    public static boolean isInfoEnabled(Object locusObject) {
        Logger l = Logger.getLogger(locusObject.getClass());
        return l.isInfoEnabled();
    }

    /**
     * Log van een debug statement.
     *
     * @param locus instance of class (voor static invocation context) - zal
     *              gebruikt worden als logger
     * @param msg   String... met berichteh - worden geconcateneerd zonder spaties
     */
    public static void debug(Object locus, String... msg) {
        Logger l = Logger.getLogger(locus.getClass());
        if (!l.isDebugEnabled()) {
            return;
        }
        String message = constructMessage(msg);
        l.debug(message);
    }

    /**
     * Log van een debug statement.
     *
     * @param locus string - zal gebruikt worden als logger
     * @param msg   String... met berichteh - worden geconcateneerd zonder spaties
     */
    public static void debug(String locus, String... msg) {
        Logger l = Logger.getLogger(locus);
        if (!l.isDebugEnabled()) {
            return;
        }
        String message = constructMessage(msg);
        l.debug(message);
    }

    /**
     * Log van een debug statement met dump van een object. Het object wordt
     * gedumpt zonder static instance variabelen in een lijst { variable = value
     * } (voorlopig) is de dump niet recursief - maw de memebers worden niet op
     * hun beurt gedumpt.
     *
     * @param locus     instance of class (voor static invocation context) - zal
     *                  gebruikt worden als logger
     * @param onderwerp Object element dat geduppt wordt. Static
     * @param msg       String... met berichteh - worden geconcateneerd zonder spaties
     */
    public static void debug(Object locus, Object onderwerp, String... msg) {
        Logger l = Logger.getLogger(locus.getClass());
        if (!l.isDebugEnabled()) {
            return;
        }
        String message = constructMessage(msg);
        if (msg != null) {
            l.debug(message + " * " + ObjectDumper.dump(onderwerp));
        }
    }

    /**
     * Log van een debug statement met dump van een object. Het object wordt
     * gedumpt zonder static instance variabelen in een lijst { variable = value
     * } (voorlopig) is de dump niet recursief - maw de memebers worden niet op
     * hun beurt gedumpt.
     *
     * @param locus     string - zal gebruikt worden als logger
     * @param onderwerp Object element dat geduppt wordt. Static
     * @param msg       String... met berichteh - worden geconcateneerd zonder spaties
     */
    public static void debug(String locus, Object onderwerp, String... msg) {
        Logger l = Logger.getLogger(locus);
        if (!l.isDebugEnabled()) {
            return;
        }
        String message = constructMessage(msg);
        if (msg != null) {
            l.debug(message + " * " + ObjectDumper.dump(onderwerp));
        }
    }

    /**
     * Log van een info statement.
     *
     * @param locus instance of class (voor static invocation context) - zal
     *              gebruikt worden als logger (maw this)
     * @param msg   String... met berichteh - worden geconcateneerd zonder spaties
     */
    public static void info(Object locus, String... msg) {
        Logger l = Logger.getLogger(locus.getClass());
        if (!l.isInfoEnabled()) {
            return;
        }
        String message = constructMessage(msg);
        l.info(message);
    }

    /**
     * Log van een info statement.
     *
     * @param locus string - zal gebruikt worden als logger (maw this)
     * @param msg   String... met berichteh - worden geconcateneerd zonder spaties
     */
    public static void info(String locus, String... msg) {
        Logger l = Logger.getLogger(locus);
        if (!l.isInfoEnabled()) {
            return;
        }
        String message = constructMessage(msg);
        l.info(message);
    }

    /**
     * Log van een info object (bv. een outputstream).
     *
     * @param locus string - zal gebruikt worden als logger (maw this)
     * @param msg   Object het object dat moet gelogged worden.
     */
    public static void info(String locus, Object msg) {
        Logger l = Logger.getLogger(locus);
        if (!l.isInfoEnabled()) {
            return;
        }
        l.info(msg);
    }


    /**
     * Log van een info statement.
     *
     * @param locus instance of class (voor static invocation context) - zal
     *              gebruikt worden als logger (maw this)
     * @param msg   String... met berichteh - worden geconcateneerd zonder spaties
     */
    public static void warn(Object locus, String... msg) {
        Logger l = Logger.getLogger(locus.getClass());
        String message = constructMessage(msg);
        l.warn(message);
    }

    /**
     * Log van een warning statement.
     *
     * @param locus string - zal gebruikt worden als logger (maw this)
     * @param msg   String... met berichteh - worden geconcateneerd zonder spaties
     */
    public static void warn(String locus, String... msg) {
        Logger l = Logger.getLogger(locus);
        String message = constructMessage(msg);
        l.warn(message);
    }

    /**
     * Log van een error object (bv. een outputstream).
     *
     * @param locus string - zal gebruikt worden als logger (maw this)
     * @param msg   Object het object dat moet gelogged worden.
     */
    public static void error(String locus, Object msg) {
        Logger l = Logger.getLogger(locus);
        l.error(msg);
    }

    /**
     * Logt de exception in een flowerbox
     *
     * @param locus instance of class (voor static invocation context) - zal
     *              gebruikt worden als logger
     * @param t     Throwable te loggen
     * @param msg   String... met berichteh - worden geconcateneerd zonder spaties
     */
    public static void error(Object locus, Throwable t, String... msg) {
        Logger l = Logger.getLogger(locus.getClass());
        String message = constructMessage(msg);
        l.error(buildErrorMessage(message, t)); // only error for the moment
    }

    /**
     * Logt de exception in een flowerbox
     *
     * @param locus string - zal gebruikt worden als logger.
     * @param t     Throwable te loggen
     * @param msg   String... met berichten - worden geconcateneerd zonder spaties
     */
    public static void error(String locus, Throwable t, String... msg) {
        Logger l = Logger.getLogger(locus);
        String message = constructMessage(msg);
        l.error(buildErrorMessage(message, t)); // only error for the moment
    }

    /**
     * Logt de exception in een flowerbox
     *
     * @param locus instance of class (voor static invocation context) - zal
     *              gebruikt worden als logger
     * @param t     Throwable te loggen
     */
    public static void error(Object locus, Throwable t) {
        Logger l = Logger.getLogger(locus.getClass());
        l.error(buildErrorMessage(null, t)); // only error for the moment
    }

    /**
     * Logt de exception in een flowerbox
     *
     * @param locus string zal gebruikt worden als logger
     * @param t     Throwable te loggen
     */
    public static void error(String locus, Throwable t) {
        Logger l = Logger.getLogger(locus);
        l.error(buildErrorMessage(null, t)); // only error for the moment
    }

    /**
     * Logt de error message - geen exception flower box te gebruiken als
     * Exceptionlog.error(this, ...
     *
     * @param locus Object commons logger
     * @param msg   String tekst te tonen
     */
    public static void error(Object locus, String... msg) {
        Logger l = Logger.getLogger(locus.getClass());
        String message = constructMessage(msg);
        l.error(message); // only error for the moment
    }

    /**
     * Logt de error message - geen exception flower box te gebruiken als
     * Exceptionlog.error(this, ...
     *
     * @param locus string zal gebruikt worden als logger
     * @param msg   String tekst te tonen
     */
    public static void error(String locus, String... msg) {
        Logger l = Logger.getLogger(locus);
        String message = constructMessage(msg);
        l.error(message); // only error for the moment
    }

    /**
     * Logt de exception in een flowerbox
     *
     * @param locus instance of class (voor static invocation context) - zal
     *              gebruikt worden als logger
     * @param t     Throwable te loggen
     * @param msg   String... met berichteh - worden geconcateneerd zonder spaties
     */
    public static void fatal(Object locus, Throwable t, String... msg) {
        Logger l = Logger.getLogger(locus.getClass());
        String message = constructMessage(msg);
        l.fatal(buildErrorMessage(message, t)); // only error for the moment
    }

    /**
     * Logt de exception in een flowerbox
     *
     * @param locus string - zal gebruikt worden als logger.
     * @param t     Throwable te loggen
     * @param msg   String... met berichten - worden geconcateneerd zonder spaties
     */
    public static void fatal(String locus, Throwable t, String... msg) {
        Logger l = Logger.getLogger(locus);
        String message = constructMessage(msg);
        l.fatal(buildErrorMessage(message, t)); // only error for the moment
    }

    /**
     * Logt de exception in een flowerbox
     *
     * @param locus instance of class (voor static invocation context) - zal
     *              gebruikt worden als logger
     * @param t     Throwable te loggen
     */
    public static void fatal(Object locus, Throwable t) {
        Logger l = Logger.getLogger(locus.getClass());
        l.fatal(buildErrorMessage(null, t)); // only error for the moment
    }

    /**
     * Logt de exception in een flowerbox
     *
     * @param locus string zal gebruikt worden als logger
     * @param t     Throwable te loggen
     */
    public static void fatal(String locus, Throwable t) {
        Logger l = Logger.getLogger(locus);
        l.fatal(buildErrorMessage(null, t)); // only error for the moment
    }

    /**
     * Logt de error message - geen exception flower box te gebruiken als
     * Exceptionlog.error(this, ...
     *
     * @param locus Object commons logger
     * @param msg   String tekst te tonen
     */
    public static void fatal(Object locus, String... msg) {
        Logger l = Logger.getLogger(locus.getClass());
        String message = constructMessage(msg);
        l.fatal(message); // only error for the moment
    }

    /**
     * Logt de error message - geen exception flower box te gebruiken als
     * Exceptionlog.error(this, ...
     *
     * @param locus string zal gebruikt worden als logger
     * @param msg   String tekst te tonen
     */
    public static void fatal(String locus, String... msg) {
        Logger l = Logger.getLogger(locus);
        String message = constructMessage(msg);
        l.fatal(message); // only error for the moment
    }

    /**
     * Bouwt een flowerbox van deexception, verzamelt alle messages aan de top.
     *
     * @param msg String bericht meegegegevsn bij de aanroep van de text
     * @param t   Throwable uit te loggen
     * @return de flowerbox message
     */
    protected static String buildErrorMessage(String msg, Throwable t) {
        StringBuffer errLogMsg = new StringBuffer(2048);
        int id = getExceptionID(t);
        errLogMsg.append("\n");
        errLogMsg
                .append("_______________________________________________________________________ Start of Exception ["
                        + id + "] *\n");

        errLogMsg.append("[Messages]\n");
        StringBuffer errorMessages = new StringBuffer(512);
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
        errLogMsg.append("\n[Stacktrace of root exception]\n");
        appendRootStackTrace(t, errLogMsg);
        errLogMsg
                .append("¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯  End of Exception ["
                        + id + "] *\n");
        return errLogMsg.toString();
    }

    /**
     * This method appends the root staktrace to the error.
     *
     * @param t         Throwable
     * @param errLogMsg StringBuffer
     */
    protected static void appendRootStackTrace(Throwable t,
                                               StringBuffer errLogMsg) {
        Throwable rootCause = getRootCause(t);
        String printedStackTrace = getPrintedStackTrace(rootCause);
        if (printedStackTrace.length() > 0) {
            errLogMsg.append(printedStackTrace);
            errLogMsg.append("\n");
            if (printedStackTrace.length() < 100) {
                // abonormal short stacktrace detected -> also error stacktrace
                // of parent parent of root
                errLogMsg
                        .append("\n[Abnormaal korte stacktrace -> stacktrace van ouder]\n");
                Throwable parent = getParentOfRootCause(t);
                if (parent != null) {
                    String backupTrace = getPrintedStackTrace(parent);
                    errLogMsg.append(backupTrace);
                    errLogMsg.append("\n");
                } else {
                    errLogMsg.append("(geen ouder exception beschikbaar)\n");
                }
            }
        } else {
            errLogMsg.append("(geen stacktrace beschikbaar)\n");
        }
    }

    /**
     * Returns the root cause of an error.
     *
     * @param t Throwable
     * @return Throwable
     */
    public static Throwable getRootCause(Throwable t) {
        Throwable cause = null;
        if (t != null) {
            cause = t.getCause();
            if (cause != null) {
                return getRootCause(cause);
            }
        }
        return t;
    }

    /**
     * Returns the parent of the root cause of an error.
     *
     * @param t Throwable
     * @return Throwable
     */
    protected static Throwable getParentOfRootCause(Throwable t) {
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

    /**
     * Returns the error messages stored on the Exception.
     *
     * @param t             Throwable
     * @param errorMessages StringBuffer
     */
    protected static void buildErrorMessages(Throwable t,
                                             StringBuffer errorMessages) {
        Throwable cause = null;
        if (t != null) {
            cause = t.getCause();
        }
        boolean root = (cause == null); // check cause to see if we are in root
        // error
        if (root) {
            return; // don't add message of root cause (already present in
            // stacktrace)
        }
        String msg = t.getMessage(); // get message from this error
        if (msg == null) {
            msg = "(geen bericht beschikbaar)";
        }
        if (errorMessages.length() > 0) {
            errorMessages.append("\n");
        }
        errorMessages.append(getExceptionName(t));
        errorMessages.append(": ");
        errorMessages.append(msg);
        buildErrorMessages(cause, errorMessages);
    }

    /**
     * Returns the error name.
     *
     * @param t Throwable
     * @return String
     */
    protected static String getExceptionName(Throwable t) {
        String exceptionName = t.getClass().getName();
        int index = exceptionName.lastIndexOf('.');
        if (index != -1) {
            exceptionName = exceptionName.substring(index + 1, exceptionName
                    .length());
        }
        return exceptionName;
    }

    /**
     * Derives near-unique error id
     *
     * @param t Throwable
     * @return String
     */
    public static int getExceptionID(Throwable t) {
        return System.identityHashCode(t);
    }

    /**
     * Prints the stacktrace.
     *
     * @param t Throwable
     * @return String
     */
    protected static String getPrintedStackTrace(Throwable t) {
        if (t == null) {
            return "";
        }
        StringWriter sw = new StringWriter(2048);
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
    }

    /**
     * Construeert een message door append van de msg elementen. Indien msg null
     * is wordt 'no message' gegeven.
     *
     * @param msg String... message snippets
     * @return String appended message, != null
     */
    protected static String constructMessage(String... msg) {
        if (msg == null) {
            return "No Log Message";
        }
        StringBuffer sb = new StringBuffer();
        for (String i : msg) {
            sb.append(i != null ? i : "{{null}}");
        }
        return sb.toString();
    }
}