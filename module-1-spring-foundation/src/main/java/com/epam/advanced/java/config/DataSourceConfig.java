package com.epam.advanced.java.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Value("${custom.datasource.url}")
    private String databaseCustomUrl;

    @Bean
    @ConditionalOnProperty(name = "custom.datasource.enabled", havingValue = "false")
    public DataSource getDataSource(DataSourceProperties dataSourceProperties) {
        return DataSourceBuilder.create()
                .url(dataSourceProperties.getUrl())
                .driverClassName(dataSourceProperties.getDriverClassName())
                .username(dataSourceProperties.getUsername())
                .password(dataSourceProperties.getPassword())
                .build();
    }

    @Bean
    @ConditionalOnProperty(name = "custom.datasource.enabled", havingValue = "true", matchIfMissing = true)
    public DataSource getCustomDataSource(DataSourceProperties dataSourceProperties) {
        return DataSourceBuilder.create()
                .url(databaseCustomUrl)
                .driverClassName(dataSourceProperties.getDriverClassName())
                .username(dataSourceProperties.getUsername())
                .password(dataSourceProperties.getPassword())
                .build();
    }
}
