package org.onlinetaskforce.persistence.utils;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

/**
 * This class is initially based on a corresponding class of the otf project.
 * An instance of this class is bound to the current thread (typical in a servlet filter) and
 * can be used to temporarily register certain information that will be logged during exception handling.
 *
 * @author jordens
 * @since 15/03/13
 */
public class ThreadContextInfo {
    /**
     * The gebruiker id of the current user.
     */
    public static final String CURRENT_GEBRUIKER_ID = "CURRENT_GEBRUIKER_ID";
    /**
     * The id of the current request that is beïng handled.
     */
    public static final String WS_REQUEST_ID = "WS_REQUEST_ID";
    /**
     * The referte of the current vraag that is beïng handled.
     */
    public static final String VRAAG_REFERTE = "VRAAG_REFERTE";
    /**
     * The namespace of the current request that is beïng handled.
     */
    public static final String NAMESPACE = "NAMESPACE";
    /**
     * The localPart of the current request that is beïng handled.
     */
    public static final String LOCAL_PART = "LOCAL_PART";
    /**
     * The id of the current JobRun.
     */
    public static final String JOB_RUN_ID = "JOB_RUN_ID";
    /**
     * The id of the current JobRun.
     */
    public static final String WS_RESPONSE = "WS_RESPONSE";

    private static ThreadLocal<ThreadContextInfo> threadContextInfoContainer = new ThreadLocal<ThreadContextInfo>();

    /**
     * Singleton method.
     *
     * @return Returns the singleton instance.
     */
    public static ThreadContextInfo getInstance() {
        ThreadContextInfo i = threadContextInfoContainer.get();
        if (i == null) {
            i = new ThreadContextInfo();
            threadContextInfoContainer.set(i);
        }
        return i;
    }

    // - - - - - - - - - - - - - - - - </static> - - - - - - - - - - - - - - - - - -

    private Map<String, Object> objects = new Hashtable<String, Object>();
    private Integer authorizedUsersCount;

    /**
     * Remove the object value for the given key.
     *
     * @param key See description.
     */
    public void clearObject(String key) {
        objects.remove(key);
    }

    /**
     * Sets the given value for the property with the given key.
     *
     * @param key   See description.
     * @param value The value to be set. When <code>null</code> is given, the property with the given key will be removed.
     */
    public void setProperty(String key, String value) {
        setObject(key, value);
    }

    /**
     * Sets the given value for the property with the given key.
     *
     * @param key   See description.
     * @param value The value to be set. When <code>null</code> is given, the property with the given key will be removed.
     */
    public void setObject(String key, Object value) {
        if (value == null) {
            objects.remove(key);
        } else {
            objects.put(key, value);
        }
    }

    /**
     * Retrieve the property for the given key.
     *
     * @param key See description.
     * @return See description.
     */
    public String getProperty(String key) {
        return (String) objects.get(key);
    }

    /**
     * Retrieve the property for the given key.
     *
     * @param key See description.
     * @return See description.
     */
    public Object getObject(String key) {
        return objects.get(key);
    }

    /**
     * Set the id of the current user.
     *
     * @param gebruikerId See description.
     */
    public void setCurrentGebruikerId(String gebruikerId) {
        if (gebruikerId == null) {
            objects.remove(CURRENT_GEBRUIKER_ID);
        } else {
            objects.put(CURRENT_GEBRUIKER_ID, gebruikerId);
        }
    }

    /**
     * Retrieve the id of the current user.
     *
     * @return See description.
     */
    public String getCurrentGebruikerId() {
        return (String) objects.get(CURRENT_GEBRUIKER_ID);
    }

    public Integer getAuthorizedUsersCount() {
        return authorizedUsersCount;
    }

    public void setAuthorizedUsersCount(Integer authorizedUsersCount) {
        this.authorizedUsersCount = authorizedUsersCount;
    }

    /**
     * Clear all properties.
     */
    public void clear() {
        objects.clear();
        authorizedUsersCount = null;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder("ThreadContextInfo[");
        if (objects.isEmpty()) {
            b.append("\n\t(no info available)");
        } else {
            int counter = 0;
            for (Iterator i = objects.entrySet().iterator(); i.hasNext(); counter++) {
                Map.Entry entry = (Map.Entry) i.next();
                b.append("\n\t");
                b.append(counter);
                b.append(": ");
                b.append(entry.getKey());
                b.append(" = ");
                b.append(entry.getValue());
            }
        }
        b.append("\n]");
        return b.toString();
    }

}
