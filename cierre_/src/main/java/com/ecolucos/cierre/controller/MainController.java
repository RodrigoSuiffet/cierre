package com.ecolucos.cierre.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class MainController {

    @GetMapping("/inicial/{shiftId}")
    public String getShift(@PathVariable String shiftId) {
        switch (shiftId)
        {
            case "mañana":
                return "100";
            case "tarde":
                return "200";
            case "noche":
                return "300";
            default:
                return "Shift ID: " + shiftId + " - Inicial";
        }

    }

    @PostMapping("/sendCaja")
    public String sendCaja(@RequestBody String caja) {
        return null;
    }
}

