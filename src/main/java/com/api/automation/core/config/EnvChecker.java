package com.api.automation.core.config;

public class EnvChecker {

    static String getEnv() {
        String env = System.getenv("env");
        if (env == null || env.isBlank()) {
            env = "local";
        }
        return env.trim().toLowerCase();
    }
}
