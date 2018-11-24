package com.ni.piiresolver.configuration;

import com.ni.EncryptionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {
    private static final String AWS_REGION = "us-east-1";

    @Bean
    public EncryptionManager encryptionManager(@Value("${service_name}") String serviceName,
                                               @Value("${environment}") String environment) throws Exception {
        EncryptionManager encryptionManager = new EncryptionManager(AWS_REGION, serviceName, environment);
        encryptionManager.init("ip", "phone");
        return encryptionManager;
    }
}
