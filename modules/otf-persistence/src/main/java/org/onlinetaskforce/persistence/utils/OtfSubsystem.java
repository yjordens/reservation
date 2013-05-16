package org.onlinetaskforce.persistence.utils;

import org.apache.commons.lang.StringUtils;
import org.onlinetaskforce.common.exceptions.SystemException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * This class represents the subsystem specific properties and provides easy access to them.
 *
 * @author jordens
 * @since 15/03/13
 */
public final class OtfSubsystem {

    /**
     * The value of the system property 'otf.subsystem'.
     */
    public static final String CURRENT_SUBSYSTEM = System.getProperty(SystemConstants.SYSTEM_PROPERTY_KEY_SUBSYSTEM);

    /**
     * A possible value for the system property 'otf.subsystem'.
     */
    public static final String SUBSYSTEM_WEB = "Web";
    /**
     * A possible value for the system property 'otf.subsystem'.
     */
    public static final String SUBSYSTEM_JM = "JM";
    /**
     * A possible value for the system property 'otf.subsystem'.
     */
    public static final String SUBSYSTEM_WS = "WS";
    /**
     * A possible value for the system property 'otf.subsystem'.
     */
    public static final String SUBSYSTEM_WS1 = "WS1";
    /**
     * A possible value for the system property 'otf.subsystem'.
     */
    public static final String SUBSYSTEM_WS2 = "WS2";

    /**
     * The list of all possible values for the system property 'otf.subsystem'.
     */
    private static final String[] SUBSYSTEM_VALUES = {SUBSYSTEM_WEB, SUBSYSTEM_JM, SUBSYSTEM_WS, SUBSYSTEM_WS1, SUBSYSTEM_WS2};

    private static OtfSubsystem instance = new OtfSubsystem();
    private static Properties props = new Properties();

    static {
        // Read the property file.
        // We do this in a static block and not in the getInstance() method in order to keep that method free from any synchronized block.
        String env = System.getProperty(SystemConstants.SYSTEM_PROPERTY_KEY_ENVIRONMENT);
        String subsys = System.getProperty(SystemConstants.SYSTEM_PROPERTY_KEY_SUBSYSTEM);
        props = OtfPropertiesUtil.retrievePropertiesFromClasspathResource("config/" + env + subsys + "/otf-subsystem.properties");
    }

    /**
     * Factory method of this singleton.
     *
     * @return otfEnvironment
     */
    public static OtfSubsystem getInstance() {
        return instance;
    }

    private OtfSubsystem() {
    }

    /**
     * Retrieves the value of an environment specific property with the given key.
     *
     * @param key Specifies the requested property.
     * @return The value of the requested property.
     */
    public String getProperty(String key) {
        if (props == null) {
            return null;
        }
        return props.getProperty(key);
    }

    /**
     * Retrieves the value of an environment specific property with the given key.
     * If not key/value is found, the given default value is returned.
     *
     * @param key          Specifies the requested property.
     * @param defaultValue The default value if the key/value is not found for the given key.
     * @return The value of the requested property, the defaultValue if no key/value is found for the given key.
     */
    public String getProperty(String key, String defaultValue) {
        if (props == null) {
            return defaultValue;
        }
        String result = props.getProperty(key);
        return result == null ? defaultValue : result;
    }

    /**
     * Retrieves the value of a required environment specific property with the given key.
     * If no property is found with the given key, throws a SystemException.
     *
     * @param key Specifies the requested property.
     * @return The value of the requested property.
     */
    public String getRequiredProperty(String key) {
        String value = props.getProperty(key);
        if (value == null) {
            throw new SystemException("Required property '" + key + "' not found. Check the configuration!");
        }
        return value;
    }

    /**
     * Retrieves the value of a required environment specific property with the given key.
     * If no property is found with the given key, throws a SystemException.
     *
     * @param key Specifies the requested property.
     * @return The value of the requested property as a Long.
     */
    public long getRequiredLong(String key) {
        String value = getRequiredProperty(key);
        return new Long(value);
    }

    /**
     * Retrieves the value of a required environment specific property with the given key.
     * If no property is found with the given key, throws a SystemException.
     *
     * @param key Specifies the requested property.
     * @return The value of the requested property as a boolean.
     */
    public boolean getRequiredBoolean(String key) {
        String value = getRequiredProperty(key);
        return Boolean.getBoolean(value);
    }

    /**
     * Returns all properties that start with the given <code>startOfName</code>.
     *
     * @param startOfName See description.
     * @return See description.
     */
    public Properties getPropertiesStartingWith(String startOfName) {
        return getPropertiesStartingWith(startOfName, false);
    }

    /**
     * Returns all properties
     *
     * @return See description
     */
    public Properties getProperties() {
        return props;
    }

    /**
     * Returns all properties that start with the given <code>startOfName</code>.
     * If <code>reduceNames</code> is <code>true</code>, the names of the properties are shortened.
     * Then <code>startOfName</code> will be left of the key of each property.
     *
     * @param startOfName See description.
     * @param reduceNames See description.
     * @return See description.
     */
    public Properties getPropertiesStartingWith(String startOfName, boolean reduceNames) {
        if (props == null) {
            return null;
        }
        Properties subset = new Properties();
        Set<Map.Entry<Object, Object>> entries = props.entrySet();
        for (Map.Entry entry : entries) {
            String name = (String) entry.getKey();
            if (name.startsWith(startOfName)) {
                if (reduceNames) {
                    name = StringUtils.substring(name, startOfName.length());
                }
                subset.put(name, entry.getValue());
            }
        }
        return subset;
    }

    /**
     * Retrieves a list of possible values for the 'otf.subsystem' property
     *
     * @return See description.
     */
    public static List<String> retrieveSubsystemValues() {
        List<String> result = new ArrayList<String>();
        Collections.addAll(result, SUBSYSTEM_VALUES);
        return result;
    }
}
