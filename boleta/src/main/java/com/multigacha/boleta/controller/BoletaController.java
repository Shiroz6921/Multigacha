package com.multigacha.boleta.controller;

import com.multigacha.boleta.model.Boleta;
import com.multigacha.boleta.service.BoletaService;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/boletas")
@Tag(name = "Boletas", description = "Operacion Boletas")
public class BoletaController {
    @Autowired
    private BoletaService service;

    @PostMapping("/generar")
    @Schema(description = "Genera una nueva boleta de transacción de compra-venta entre clientes, incluyendo detalles como monto total, fecha y descripción de la transacción.")    
    public ResponseEntity<Boleta> generarBoleta(@RequestBody Boleta boleta) {
        return ResponseEntity.ok(service.generarBoleta(boleta));
    }
}
