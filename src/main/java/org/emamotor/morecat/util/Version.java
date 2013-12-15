package org.emamotor.morecat.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @author Yoshimasa Tanabe
 */
public class Version {

    private static final String PATH = "/version.properties";
    private static String version = null;
    private static final Version uniqueInstance = new Version();

    private Version() {}

    public static Version getInstance() {
        return uniqueInstance;
    }

    public String getVersion() {

        if (this.version != null) return this.version;

        Properties properties = new Properties();

        try (InputStream is = getClass().getResourceAsStream(PATH);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {

            properties.load(reader);
            this.version = properties.getProperty("version", "UNKNOWN");
        } catch (IOException ioe) {
            throw new IllegalStateException("version.properties may be invalid", ioe);
        }

        return this.version;

    }

}
