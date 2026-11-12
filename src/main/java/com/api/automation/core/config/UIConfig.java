package com.api.automation.core.config;

import java.util.Properties;

public final class UIConfig {

    private static Properties properties;

    private static UIConfig apiConfig;

    private static String env;

    private UIConfig() {
        env = EnvChecker.getEnv();
        properties = ConfigLoader.getProperties(env + "_ui");
    }

    public static UIConfig getInstance() {
        if (apiConfig == null) {
            synchronized ((UIConfig.class)) {
                if (apiConfig == null) {
                    UIConfig.apiConfig = new UIConfig();
                }
            }
        }
        return apiConfig;
    }

}
