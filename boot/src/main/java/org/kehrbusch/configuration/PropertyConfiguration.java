package org.kehrbusch.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.io.*;
import java.util.Properties;
import java.util.logging.Logger;

@org.springframework.context.annotation.Configuration
public class PropertyConfiguration {
    @Value("${properties.path}")
    private String propertiesPath;

    private static final Logger logger = Logger.getLogger(PropertyConfiguration.class.getName());

    @Bean
    @Qualifier("properties")
    public Properties loadProperties() {
        Properties properties = new Properties();
        try {
            File file = new File(propertiesPath);
            InputStream br = new FileInputStream(file);
            properties.load(br);
        } catch (Exception e) {
            logger.warning(e.toString());
        }
        return properties;
    }
}
