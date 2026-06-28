package com.example.otclients.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
//enables the injection of properties from application.properties
@EnableConfigurationProperties
@ConfigurationPropertiesScan(basePackages = "com.integrator.application")
public class AppInfo {

    @Value("${app.name}")
    private String appName;

    @Value("${app.version}")
    private String appVersion;

    @Value("${spring.profiles.active:dev}")
    private String profile;
    @Value("${spring.datasource.url}")
    private String dataSourceUrl;
    @Value("${spring.datasource.username}")
    private String dataSourceUsername;
    @Value("${spring.datasource.password}")
    private String dataSourcePassword;

    @Value("${redis.host:localhost}")
    private String redisHost;
    @Value("${redis.password:localhost}")
    private String redisPassword;
    @Value("${redis.port:26379}")
    private int redisPort;
    @Value("${redis.ttl:1440}")
    private long redisTimeToLive;
}
