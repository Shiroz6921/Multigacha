package com.multigacha.boleta.controller;

import com.multigacha.boleta.model.Boleta;
import com.multigacha.boleta.service.BoletaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/boletas")
public class BoletaController {
    @Autowired
    private BoletaService service;

    @PostMapping("/generar")
    public ResponseEntity<Boleta> generarBoleta(@RequestBody Boleta boleta) {
        return ResponseEntity.ok(service.generarBoleta(boleta));
    }
}
