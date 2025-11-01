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
import java.security.SecureRandom;

@Component
public class FileUploadUtil {

    private final FileStorageProperties fileStorageProperties;

    public FileUploadUtil(FileStorageProperties fileStorageProperties) {
        this.fileStorageProperties = fileStorageProperties;
    }

    /**
     * Sauvegarde un fichier dans le répertoire d'uploads avec un code unique préfixé.
     *
     * @param fileName      Le nom original du fichier.
     * @param multipartFile Le fichier Multipart à sauvegarder.
     * @return Le code unique généré pour le fichier.
     * @throws IOException Si une erreur I/O survient lors de la sauvegarde.
     */
    public String saveFile(String fileName, MultipartFile multipartFile) throws IOException {
        // Nettoyage du nom de fichier pour éviter les problèmes de sécurité
        String cleanFileName = Path.of(fileName).getFileName().toString();

        // Création du chemin d’upload si nécessaire
        Path uploadPath = Paths.get(fileStorageProperties.getUploadDir());
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        // Génération d’un code unique alphanumérique sans méthode dépréciée
        SecureRandom random = new SecureRandom();
        String fileCode = RandomStringUtils.random(8, 0, 0, true, true, null, random);

        // Construction du chemin final du fichier
        Path filePath = uploadPath.resolve(fileCode + "-" + cleanFileName);

        // Copie du fichier en remplaçant si déjà existant
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save file: " + fileName, ioe);
        }
        return fileCode;
    }
}
