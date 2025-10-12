package com.server.api.management.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {
    // Directory where the uploaded files will be stored
    private String uploadDir;
}
