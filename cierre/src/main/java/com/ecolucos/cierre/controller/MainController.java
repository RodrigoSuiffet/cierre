package com.ecolucos.cierre.controller;

import com.ecolucos.cierre.entities.Inicial;
import org.springframework.web.bind.annotation.*;

@RestController
public class MainController {

    @GetMapping("/inicial/{shiftId}")
    public Inicial getShift(@PathVariable String shiftId) {
        switch (shiftId)
        {
            case "mañana":
                return new Inicial(100);
            case "tarde":
                return new Inicial(200);
            case "noche":
                return new Inicial(300);
        }
        return new Inicial(0);
    }

    @PostMapping("/sendCaja")
    public String sendCaja(@RequestBody String caja) {
        return null;
    }

    }
