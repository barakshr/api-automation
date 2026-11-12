package com.api.automation.core.config;

import java.util.Properties;

public final class ApiConfig {

    private static Properties properties;

    private static ApiConfig apiConfig;

    private static String env;

    private ApiConfig() {
        env = EnvChecker.getEnv();
        properties = ConfigLoader.getProperties(env + "_api");
    }

    public static ApiConfig getInstance() {
        if (apiConfig == null) {
            synchronized ((ApiConfig.class)) {
                if (apiConfig == null) {
                    ApiConfig.apiConfig = new ApiConfig();
                }
            }
        }
        return apiConfig;
    }

    public String getEnv() {
        return env;
    }

    public String getBaseUri() {
        return ConfigRequire.required("baseUri", properties, env);
    }

    public String getConnectTimeoutMs() {
        return ConfigRequire.required("connectTimeoutMs", properties, env);
    }

    public String getReadTimeoutMs() {
        return ConfigRequire.required("baseUri", properties, env);
    }

    public String getAuthType() {
        return properties.getProperty("auth.type");
    }

}
