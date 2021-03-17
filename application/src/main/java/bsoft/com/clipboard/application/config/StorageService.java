package bsoft.com.clipboard.application.config;

import bsoft.com.clipboard.storage.config.StorageConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageService {

    @Bean
    StorageConfig getStorageConfig() {
        return new StorageConfig();
    }
}
