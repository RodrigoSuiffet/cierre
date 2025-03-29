package com.ecolucos.cierre.service;

import com.ecolucos.cierre.DTO.CashRegisterSubmissionDTO;
import com.ecolucos.cierre.entities.Inicial;
import com.ecolucos.cierre.repository.CajaRepository;
import com.ecolucos.cierre.entities.Caja;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class CajaService {

    @Autowired
    private CajaRepository cajaRepository;

    @Value("${file.upload-dir:./uploads}")
    private String uploadDir;


    public Inicial getInicial (String shiftId) {
        Inicial inicial = new Inicial();
        inicial.setValor(Double.parseDouble(cajaRepository.findLastRecuentoByTurno(shiftId)));
       return inicial;

    }

    /**
     * Save the CajaCierre entity to the database
     */
    public Caja saveCajaCierre(CashRegisterSubmissionDTO cajaCierre) {
        // You might want to add validation or business logic here
        // Convert DTO to entity
        Caja caja = new Caja();
        return cajaRepository.save(caja);
    }

    /**
     * Save an attachment file and return its ID
     */
    public Long saveAttachment(MultipartFile file) throws IOException {
        // Create the upload directory if it doesn't exist
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Generate a unique filename
        String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(filename);

        // Copy the file to the upload directory
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // In a real application, you would save the file information to a database
        // and return the generated ID
        // For this example, we'll just return a random ID
        return (long) (Math.random() * 10000);
    }

    /**
     * Retrieve an attachment by ID
     */
    public Path getAttachmentPath(Long id) {
        // In a real application, you would lookup the file path from the database
        // For this example, we'll just return null
        return null;
    }
}