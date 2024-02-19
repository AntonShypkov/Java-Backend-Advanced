package com.epam.advanced.java.actuator;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Endpoint(id = "appInfo")
@RequiredArgsConstructor
public class AppInfoActuator {

    private final Environment environment;

    @Value("${spring.datasource.url}")
    private String databaseUrl;

    private static final String APP_INFO_PROFILES_ATTRIBUTE_NAME = "PROFILES";
    private static final String APP_INFO_DB_URL_ATTRIBUTE_NAME = "DB_URL";

    @ReadOperation
    public Map<String, String> getApplicationInfo() {
        return Map.of(
                APP_INFO_PROFILES_ATTRIBUTE_NAME, String.join(", ", environment.getActiveProfiles()),
                APP_INFO_DB_URL_ATTRIBUTE_NAME, databaseUrl);
    }
}
