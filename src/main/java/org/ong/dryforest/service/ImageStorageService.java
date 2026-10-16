package org.ong.dryforest.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageStorageService {

    private final Path uploadDirectory =
            Paths.get("uploads", "incidents").toAbsolutePath().normalize();

    public String store(MultipartFile image) throws IOException {
        if (image == null || image.isEmpty()) {
            return null;
        }

        Files.createDirectories(uploadDirectory);

        String originalFilename = image.getOriginalFilename();
        String extension = "";

        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(
                    originalFilename.lastIndexOf(".")
            );
        }

        String filename = UUID.randomUUID() + extension;
        Path destination = uploadDirectory.resolve(filename).normalize();

        if (!destination.startsWith(uploadDirectory)) {
            throw new IOException("Chemin de fichier invalide");
        }

        image.transferTo(destination);

        return "uploads/incidents/" + filename;
    }
}