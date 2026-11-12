package com.api.automation.core.config;

import java.util.Properties;

public class ConfigRequire {

    static String required(String key, Properties properties, String env) {
        String value = properties.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing config key '" + key + "' for env=" + env);
        }
        return value.trim();
    }


}
