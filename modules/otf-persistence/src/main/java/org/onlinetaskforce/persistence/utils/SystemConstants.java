package org.onlinetaskforce.persistence.utils;

/**
 * This class is responsible for accessing system level properties.
 *
 * @author jordens
 * @since 15/03/13
 */
public class SystemConstants {
    /**
     * The name of the otf system.
     */
    public static final String SYSTEM_NAME = "Otf";
    /**
     * De hardcoded objectid van de gebruiker die het systeem zelf representeert.
     */
    public static final String SYSTEEM_ID = "999999";
    /**
     * Reads the System Property "org.onlinetaskforce.otf.DEVELOPMENT". When this property is true, the application will run in development mode.
     */
    public static final boolean DEVELOPMENT = Boolean.valueOf(System.getProperty("org.onlinetaskforce.otf.DEVELOPMENT"));
    /**
     * Reads the System Property "org.onlinetaskforce.otf.PERFORMANCE". When this property is true, the application will run in performance test mode.
     */
    public static final boolean PERFORMANCE = Boolean.valueOf(System.getProperty("org.onlinetaskforce.otf.PERFORMANCE"));

    private static final String ARC_WEB = "web";
    private static final String ARC_JOB = "job";
    private static final String ARC_WS = "ws";
    private static final String ARC_DEFAULT = ARC_WEB + "," + ARC_WS + "," + ARC_JOB;
    /**
     * Reads the System Property "org.onlinetaskforce.otf.APPLICATION_RUN_CONTEXT".<br>
     * This property should contain a comma separated list of these possible values:
     * <ul>
     * <li>web: The application is accessible via the web interface</li>
     * <li>job: The application will run scheduled jobs</li>
     * <li>ws: The application will provide webservices</li>
     * </ul>
     */
    private static final String APPLICATION_RUN_CONTEXT = System.getProperty("org.onlinetaskforce.otf.APPLICATION_RUN_CONTEXT", ARC_DEFAULT);

    /**
     * The key of a system property that indicates the current environment.
     */
    public static final String SYSTEM_PROPERTY_KEY_ENVIRONMENT = "otf.environment";
    /**
     * The key of a system property that indicates the current subsystem.
     */
    public static final String SYSTEM_PROPERTY_KEY_SUBSYSTEM = "otf.subsystem";


    /**
     * Checks if the application is in job context
     *
     * @return true if the application runs in job context
     */
    public static boolean isJobRunContext() {
        String[] runContexts = APPLICATION_RUN_CONTEXT.split(",");
        for (String runContext : runContexts) {
            if (ARC_JOB.equals(runContext)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the application is in web context
     *
     * @return true if the application runs in web context
     */
    public static boolean isWebRunContext() {
        String[] runContexts = APPLICATION_RUN_CONTEXT.split(",");
        for (String runContext : runContexts) {
            if (ARC_WEB.equals(runContext)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the application is in webservice context
     *
     * @return true if the application runs in webservice context
     */
    public static boolean isWsRunContext() {
        String[] runContexts = APPLICATION_RUN_CONTEXT.split(",");
        for (String runContext : runContexts) {
            if (ARC_WS.equals(runContext)) {
                return true;
            }
        }
        return false;
    }


}
