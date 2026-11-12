package com.api.automation.core.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public final class ConfigInfo {

    private static final ConfigInfo INSTANCE = new ConfigInfo();
    private static ConfigInfo configInfo;

    private String env;
    private final Properties properties;

    private ConfigInfo() {
        this.properties = ConfigLoader.getProperties();
    }

    public static ConfigInfo getConfigInfo() {
        if (configInfo == null) {
            synchronized (ConfigInfo.class) {
                if (configInfo == null) {
                    configInfo = new ConfigInfo();
                }
            }

        }
        return configInfo;
    }

    public String getEnv() {
        return env;
    }

    public String getBaseUri() {
        return required("baseUri");
    }

    public int getConnectTimeoutMs() {
        return Integer.parseInt(properties.getProperty("connectTimeoutMs", "5000"));
    }

    public int getReadTimeoutMs() {
        return Integer.parseInt(properties.getProperty("readTimeoutMs", "10000"));
    }

    public boolean isRequestLoggingEnabled() {
        return Boolean.parseBoolean(properties.getProperty("requestLogging", "true"));
    }

    public String getAuthType() {
        return properties.getProperty("auth.type", "challenger");
    }

    private String required(String key) {
        String value = properties.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing config key '" + key + "' for env=" + env);
        }
        return value.trim();
    }

    private static Properties loadProperties(String envName) {
        String path = "config/" + envName + ".properties";
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
