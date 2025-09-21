package com.example.bookstore.api.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class Config {

    private static final String CONFIG_FILE = "/config.properties";
    private static final Properties PROPS = new Properties();

    static {
        try (InputStream is = Config.class.getResourceAsStream(CONFIG_FILE)) {
            if (is != null) {
                PROPS.load(is);
            } else {
                throw new RuntimeException("Could not load " + CONFIG_FILE);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read " + CONFIG_FILE, e);
        }
    }

    private Config() {}

    public static String baseUrl() {
        return System.getProperty("baseUrl", System.getenv().getOrDefault("BASE_URL",
                PROPS.getProperty("baseUrl", "https://fakerestapi.azurewebsites.net")));
    }

    public static String apiBasePath() {
        return System.getProperty("apiBasePath", System.getenv().getOrDefault("API_BASE_PATH",
                PROPS.getProperty("apiBasePath", "/api/v1")));
    }

    public static boolean useVersionedMediaType() {
        String sys = System.getProperty("useVersionedMediaType", System.getenv().getOrDefault("USE_VERSIONED_MEDIA_TYPE",
                PROPS.getProperty("useVersionedMediaType", "false")));
        return "true".equalsIgnoreCase(sys);
    }

    public static String versionedMediaType() {
        return System.getProperty("versionedMediaType", System.getenv().getOrDefault("VERSIONED_MEDIA_TYPE",
                PROPS.getProperty("versionedMediaType", "application/json; v=1.0")));
    }

    public static boolean logOnFailure() {
        String sys = System.getProperty("logOnFailure", System.getenv().getOrDefault("LOG_ON_FAILURE",
                PROPS.getProperty("logOnFailure", "true")));
        return "true".equalsIgnoreCase(sys);
    }
}
