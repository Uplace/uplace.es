package com.arnaugarcia.uplace.config;

import com.cloudinary.Cloudinary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    private final Logger log = LoggerFactory.getLogger(CloudinaryConfig.class);

    private ApplicationProperties appProperties;

    public CloudinaryConfig(ApplicationProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Bean
    public Cloudinary getCloudinary() {
        log.debug("Initializing Cloudinary {} - {} - {}",
            appProperties.getCloudinary().getName(),
            appProperties.getCloudinary().getKey(),
            appProperties.getCloudinary().getSecret());
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", appProperties.getCloudinary().getName());
        config.put("api_key", appProperties.getCloudinary().getKey());
        config.put("api_secret", appProperties.getCloudinary().getSecret());
        return new Cloudinary(config);
    }
}
