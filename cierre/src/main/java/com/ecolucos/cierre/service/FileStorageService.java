package com.ecolucos.cierre.service;


import com.ecolucos.cierre.entities.Attachment;
//import com.ecolucos.cierre.repository.AttachmentRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    //@Autowired
   // private AttachmentRepository attachmentRepository;

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
     * Store a single file and return the saved attachment entity
     */
    public Attachment storeFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException("Failed to store empty file");
        }

        // Normalize file name
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());

        // Check if the file's name contains invalid characters
        if (originalFileName.contains("..")) {
            throw new RuntimeException("Filename contains invalid path sequence " + originalFileName);
        }

        // Generate a unique file name to prevent duplicates
        String uniqueFileName = UUID.randomUUID().toString() + "_" + originalFileName;

        // Copy file to the target location (replacing existing file with the same name)
        Path targetLocation = this.fileStorageLocation.resolve(uniqueFileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        // Create and save an attachment entity
        Attachment attachment = new Attachment();
        attachment.setFileName(originalFileName);
        attachment.setStoredFileName(uniqueFileName);
        attachment.setFileType(file.getContentType());
        attachment.setSize(file.getSize());

        return attachment;//attachmentRepository.save(attachment);
    }

    /**
     * Store multiple files and return a list of saved attachment entities
     */
    public List<Attachment> storeFiles(MultipartFile[] files) throws IOException {
        List<Attachment> savedAttachments = new ArrayList<>();

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                savedAttachments.add(storeFile(file));
            }
        }

        return savedAttachments;
    }

    /**
     * Get the file path for a specific attachment
     */
    /*
    public Path getFilePath(Long attachmentId) {
        Attachment attachment = attachmentRepository.findById(attachmentId)
                .orElseThrow(() -> new RuntimeException("Attachment not found with id " + attachmentId));

        return this.fileStorageLocation.resolve(attachment.getStoredFileName());
    }

     */
}