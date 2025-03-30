package com.ecolucos.cierre.service;

import com.ecolucos.cierre.entities.db.Attachment;
import com.ecolucos.cierre.entities.db.Caja;
import com.ecolucos.cierre.repository.AttachmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@Slf4j
public class FileStorageService {

    private final Path fileStorageLocation;

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    public FileStorageService(@Value("${file.upload-dir:./uploads}") String uploadDir) {
        this.fileStorageLocation = Paths.get(uploadDir)
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    /**
     * Store a file and associate it with a cash register
     */
    public Attachment storeFile(MultipartFile file, Caja caja) throws IOException {
        // Normalize file name
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());

        // Check if the file's name contains invalid characters
        if (originalFileName.contains("..")) {
            throw new RuntimeException("Filename contains invalid path sequence " + originalFileName);
        }

        // Generate a unique file name to prevent duplicates
        String uniqueFileName = UUID.randomUUID() + "_" + originalFileName;

        // Create a subfolder for this cash register
        String cajaFolder = "caja_" + caja.getId();
        Path cajaPath = this.fileStorageLocation.resolve(cajaFolder);
        if (!Files.exists(cajaPath)) {
            Files.createDirectories(cajaPath);
        }

        // Copy file to the target location (replacing existing file with the same name)
        Path targetLocation = cajaPath.resolve(uniqueFileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        log.info("File stored successfully: {} for cash register ID: {}", targetLocation, caja.getId());

        // Create and save an attachment entity
        Attachment attachment = new Attachment();
        attachment.setFileName(originalFileName);
        attachment.setStoredFileName(uniqueFileName);
        attachment.setFileType(file.getContentType());
        attachment.setSize(file.getSize());
        attachment.setCaja(caja);

        return attachmentRepository.save(attachment);
    }

    /**
     * Legacy method without cajaId for backward compatibility
     */
    public Attachment storeFile(MultipartFile file) throws IOException {
        // For backward compatibility, we'll use a default folder
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());

        if (originalFileName.contains("..")) {
            throw new RuntimeException("Filename contains invalid path sequence " + originalFileName);
        }

        String uniqueFileName = UUID.randomUUID().toString() + "_" + originalFileName;
        Path targetLocation = this.fileStorageLocation.resolve(uniqueFileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        Attachment attachment = new Attachment();
        attachment.setFileName(originalFileName);
        attachment.setStoredFileName(uniqueFileName);
        attachment.setFileType(file.getContentType());
        attachment.setSize(file.getSize());

        return attachmentRepository.save(attachment);
    }
}