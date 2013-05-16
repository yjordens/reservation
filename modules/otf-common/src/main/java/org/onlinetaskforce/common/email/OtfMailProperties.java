package org.onlinetaskforce.common.email;

import org.onlinetaskforce.common.utils.OtfPropertiesUtil;

import java.util.Properties;

/**
 * @author jordens
 * @since 14/05/13
 */
public final class OtfMailProperties {
    private static OtfMailProperties instance = new OtfMailProperties();
    private static Properties props;

    static {
        // Read the property file.
        // We do this in a static block and not in the getInstance() method in order to keep that method free from any synchronized block.
        props = OtfPropertiesUtil.retreivePropertiesFromClasspathResource("org/onlinetaskforce/common/utils/config/otf-mail.properties");
    }

    /**
     * Factory method for this singleton
     *
     * @return DiscimusEnvironment
     */
    public static OtfMailProperties getInstance() {
        return instance;
    }


    private OtfMailProperties() {
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
    }}
