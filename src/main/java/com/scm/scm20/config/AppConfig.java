package com.scm.scm20.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${cloudinary.cloud.name}")
    private String cloudName;

    @Value("${cloudinary.api.key}")
    private String clodinaryApiKey;

    @Value("${cloudinary.api.secret}")
    private String clodinaryApiSecret;

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(
                ObjectUtils.asMap(
                        "cloud_name",cloudName,
                        "api_key",clodinaryApiKey,
                        "api_secret",clodinaryApiSecret
                )
        );
    }

}
