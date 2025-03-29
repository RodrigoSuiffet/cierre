package com.ecolucos.cierre.controller;

import com.ecolucos.cierre.DTO.CashRegisterSubmissionDTO;
import com.ecolucos.cierre.repository.CajaRepository;
import com.ecolucos.cierre.entities.Attachment;
import com.ecolucos.cierre.entities.Caja;
import com.ecolucos.cierre.entities.Inicial;
import com.ecolucos.cierre.service.CajaService;
import com.ecolucos.cierre.service.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
     */
    @PostMapping("/api/submit")
    public ResponseEntity<Map<String, Object>> submitCaja(@RequestBody CashRegisterSubmissionDTO cajaCierre) {
        try {
            // Save the cash register data
            Caja saved = cajaService.saveCajaCierre(cajaCierre);

            // Return success response
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("id", saved.getId());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", e.getMessage());

            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint to handle multiple file uploads at once
     */
    @PostMapping("/api/upload-multiple")
    public ResponseEntity<Map<String, Object>> uploadMultipleFiles(
            @RequestParam("files") MultipartFile[] files) {

        try {
            List<Long> attachmentIds = new ArrayList<>();
            List<String> fileNames = new ArrayList<>();

            // Process each file
            for (MultipartFile file : files) {
                Attachment savedAttachment = fileStorageService.storeFile(file);
                attachmentIds.add(savedAttachment.getId());
                fileNames.add(savedAttachment.getFileName());
            }

            // Return response with all file IDs
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("attachmentIds", attachmentIds);
            response.put("fileNames", fileNames);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", e.getMessage());

            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Single file upload endpoint (kept for compatibility)
     */
    @PostMapping("/api/upload")
    public ResponseEntity<Map<String, Object>> uploadFile(
            @RequestParam("file") MultipartFile file) {

        try {
            Attachment savedAttachment = fileStorageService.storeFile(file);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("id", savedAttachment.getId());
            response.put("fileName", savedAttachment.getFileName());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", e.getMessage());

            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    }
