package org.onlinetaskforce.persistence.utils;

import org.onlinetaskforce.common.exceptions.SystemException;
import org.onlinetaskforce.common.log.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utility to retrieve files from the classpath
 *
 * @author jordens
 * @since 15/03/13
 */
public class OtfPropertiesUtil {

    /**
     * Retreive properties from a given resource on the classpath
     *
     * @param path See description
     * @return See description
     */
    public static Properties retrievePropertiesFromClasspathResource(String path) {
        Properties props = null;

        // Read the property file.
        // We do this in a static block and not in the getInstance() method in order to keep that method free from any synchronized block.
        ClassLoader classLoader = OtfPropertiesUtil.class.getClassLoader();
        try {
            props = retreivePropertiesFromInputSteam(classLoader.getResourceAsStream(path));
        } catch(Exception e) {  
            Log.error(e);
        }

        return props;
    }

    /**
     * Retreive properties from a given resource (absolute path)
     *
     * @param path See description
     * @return See description
     */
    public static Properties retreivePropertiesFromResource(String path) {
        Properties props = null;
        File file = new File(path);

        if(file.exists()) {
            try {
                props = retreivePropertiesFromInputSteam(new FileInputStream(file));
            } catch (FileNotFoundException e) {
                Log.error(e);
            }
        }

        return props;
    }

    private static Properties retreivePropertiesFromInputSteam(InputStream input) {
        Properties props = new Properties();

        try {
            props.load(input);
        } catch (IOException e) {
            throw new SystemException(e);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    throw new SystemException(e);
                }
            }
        }

        return props;
    }
}
