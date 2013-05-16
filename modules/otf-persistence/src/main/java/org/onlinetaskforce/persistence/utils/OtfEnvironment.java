package org.onlinetaskforce.persistence.utils;

import org.apache.commons.lang.StringUtils;
import org.onlinetaskforce.common.exceptions.SystemException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * This class is initially based on a corresponding class of the otf project.
 * This class represents the environment specific properties and provides easy access to them.
 *
 * @author jordens
 * @since 15/03/13
 */
public final class OtfEnvironment {

    /**
     * The value of the system property 'otf.environment'.
     */
    public static final String CURRENT_ENVIRONMENT = System.getProperty(SystemConstants.SYSTEM_PROPERTY_KEY_ENVIRONMENT);

    /**
     * A possible value for the system property 'otf.environment'.
     */
    public static final String ENV_LOCAL = "Local";
    /**
     * A possible value for the system property 'otf.environment'.
     */
    public static final String ENV_LOCAL_NO_CONTAINER = "LocalNoContainer";
    /**
     * A possible value for the system property 'otf.environment'.
     */
    public static final String ENV_UFT = "Uft";
    /**
     * A possible value for the system property 'otf.environment'.
     */
    public static final String ENV_CI = "Ci";
    /**
     * A possible value for the system property 'otf.environment'.
     */
    public static final String ENV_PERF = "Perf";
    /**
     * A possible value for the system property 'otf.environment'.
     */
    public static final String ENV_BRANCH = "Branch";
    /**
     * A possible value for the system property 'otf.environment'.
     */
    public static final String ENV_BRANCHCI = "BranchCi";
    /**
     * A possible value for the system property 'otf.environment'.
     */
    public static final String ENV_TI = "Ti";
    /**
     * A possible value for the system property 'otf.environment'.
     */
    public static final String ENV_ETIO = "Etio";
    /**
     * A possible value for the system property 'otf.environment'.
     */
    public static final String ENV_PRODUCTION = "Production";

    /**
     * The list of all possible values for the system property 'otf.environment'.
     */
    private static final String[] ENV_VALUES = {ENV_LOCAL, ENV_LOCAL_NO_CONTAINER, ENV_UFT, ENV_BRANCHCI, ENV_BRANCH, ENV_ETIO, ENV_TI, ENV_PRODUCTION, ENV_PERF, ENV_CI};

    private static OtfEnvironment instance = new OtfEnvironment();
    private static Properties props;

    static {
        // Read the property file.
        // We do this in a static block and not in the getInstance() method in order to keep that method free from any synchronized block.
        String env = System.getProperty(SystemConstants.SYSTEM_PROPERTY_KEY_ENVIRONMENT);
        props = OtfPropertiesUtil.retrievePropertiesFromClasspathResource("config/" + env + "/otf-environment.properties");
    }

    /**
     * Factory method for this singleton
     *
     * @return OtfEnvironment
     */
    public static OtfEnvironment getInstance() {
        return instance;
    }

    /**
     * Retrieves a list of possible values for the 'otf.environment' property
     *
     * @return See description.
     */
    public static List<String> retrieveEnvironmentValues() {
        List<String> result = new ArrayList<String>();
        for (String value : ENV_VALUES) {
            result.add(value);
        }
        return result;
    }

    private OtfEnvironment() {
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
        return Boolean.parseBoolean(value);
    }

    /**
     * Retrieves the value of a required environment specific property with the given key.
     * If no property is found with the given key, throws a SystemException.
     *
     * @param key          Specifies the requested property.
     * @param defaultValue The default value for when the requested property is not found.
     * @return The value of the requested property as a boolean.
     */
    public boolean getBoolean(String key, boolean defaultValue) {
        String value = getProperty(key);
        if (StringUtils.isEmpty(value)) {
            return defaultValue;
        }
        return Boolean.parseBoolean(value);
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
}
