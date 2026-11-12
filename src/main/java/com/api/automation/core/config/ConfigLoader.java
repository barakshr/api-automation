package com.api.automation.core.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class ConfigLoader {

    protected static Properties getProperties(String configFile) {


        String path = "config/" + configFile + ".properties";
        Properties props = new Properties();
        try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(path)) {
            if (in == null) {
                throw new IllegalStateException("Config file not found on classpath: " + path);
            }
            props.load(in);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load config: " + path, e);
        }
        Objects.requireNonNull(props);
        return props;
    }


}
