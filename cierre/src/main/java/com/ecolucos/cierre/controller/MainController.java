package com.ecolucos.cierre.controller;

import com.ecolucos.cierre.entities.DTO.CajaDTO;
import com.ecolucos.cierre.repository.CajaRepository;
import com.ecolucos.cierre.entities.db.Attachment;
import com.ecolucos.cierre.entities.db.Caja;
import com.ecolucos.cierre.entities.db.Inicial;
import com.ecolucos.cierre.service.CajaService;
import com.ecolucos.cierre.service.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
public class MainController {

    @Autowired
    private CajaRepository cajaRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private CajaService cajaService;

    @GetMapping("/inicial/{shiftId}")
    public Inicial getShift(@PathVariable String shiftId) {
        return this.cajaService.getInicial(shiftId);
    }

    @PostMapping("/sendCaja")
    public String sendCaja(@RequestBody String caja) {
        return null;
    }

    /**
     * Endpoint to handle the main cash register submission
     * This now only creates the basic cash register record without attachments
     */
    @PostMapping("/api/submit")
    public ResponseEntity<Map<String, Object>> submitCaja(@RequestBody CajaDTO cajaCierre) {
        try {
            // Save the cash register data without attachments
            Caja saved = cajaService.saveCajaCierre(cajaCierre);

            // Return success response with the cash register ID
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("id", saved.getId());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error saving cash register", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", e.getMessage());

            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint to handle multiple file uploads at once
     * Now requires the cajaId to associate the files with the correct cash register
     */
    @PostMapping("/api/upload-multiple/{cajaId}")
    public ResponseEntity<Map<String, Object>> uploadMultipleFiles(
            @PathVariable Long cajaId,
            @RequestParam("files") MultipartFile[] files) {

        try {
            log.info("Uploading {} files for cash register ID: {}", files.length, cajaId);

            // Verify the cash register exists
            Caja caja = cajaService.getCajaById(cajaId);
            if (caja == null) {
                throw new IllegalArgumentException("Cash register with ID " + cajaId + " not found");
            }

            List<Long> attachmentIds = new ArrayList<>();
            List<String> fileNames = new ArrayList<>();

            // Process each file and associate with the cash register ID
            for (MultipartFile file : files) {
                Attachment savedAttachment = fileStorageService.storeFile(file, caja);
                attachmentIds.add(savedAttachment.getId());
                fileNames.add(savedAttachment.getFileName());
            }

            // Return response with all file IDs
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("attachmentIds", attachmentIds);
            response.put("fileNames", fileNames);
            response.put("cajaId", cajaId);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error uploading files for cash register ID: " + cajaId, e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", e.getMessage());

            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Single file upload endpoint (kept for compatibility)
     * Now requires the cajaId to associate the file with the correct cash register
     */
    @PostMapping("/api/upload/{cajaId}")
    public ResponseEntity<Map<String, Object>> uploadFile(
            @PathVariable Long cajaId,
            @RequestParam("file") MultipartFile file) {

        try {
            log.info("Uploading single file for cash register ID: {}", cajaId);

            // Verify the cash register exists
            Caja caja = cajaService.getCajaById(cajaId);
            if (caja == null) {
                throw new IllegalArgumentException("Cash register with ID " + cajaId + " not found");
            }

            Attachment savedAttachment = fileStorageService.storeFile(file, caja);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("id", savedAttachment.getId());
            response.put("fileName", savedAttachment.getFileName());
            response.put("cajaId", cajaId);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error uploading file for cash register ID: " + cajaId, e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", e.getMessage());

            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}