package com.server.api.management.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class FileUploadUtil {

    private final FileStorageProperties fileStorageProperties;

    public FileUploadUtil(FileStorageProperties fileStorageProperties) {
        this.fileStorageProperties = fileStorageProperties;
    }

    /**
     * Saves a file to the upload directory with a unique code prefixed to the filename.
     *
     * @param fileName      The original name of the file.
     * @param multipartFile The MultipartFile to be saved.
     * @return The unique code generated for the file.
     * @throws IOException If an I/O error occurs during file saving.
     */
    public String saveFile(String fileName, MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(fileStorageProperties.getUploadDir());
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        String fileCode = RandomStringUtils.randomAlphanumeric(8);
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileCode + "-" + fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save file: " + fileName, ioe);
        }
        return fileCode;
    }
}
