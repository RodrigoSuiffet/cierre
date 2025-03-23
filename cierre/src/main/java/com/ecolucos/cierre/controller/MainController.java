package com.ecolucos.cierre.controller;

import com.ecolucos.cierre.entities.Inicial;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
@CrossOrigin("http://localhost:3000")
@RestController
public class MainController {

    @GetMapping("/inicial/{shiftId}")
    public float getShift(@PathVariable String shiftId) {
        switch (shiftId)
        {
            case "mañana":
                return 100;
            case "tarde":
                return 200;
            case "noche":
                return 300;
        }
        return 0;
    }

    @PostMapping("/sendCaja")
    public String sendCaja(@RequestBody String caja) {
        return null;
    }

    }
