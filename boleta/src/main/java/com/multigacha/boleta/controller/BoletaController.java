package com.multigacha.boleta.controller;

import com.multigacha.boleta.model.Boleta;
import com.multigacha.boleta.service.BoletaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

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
    @Operation(summary = "Genera una boleta nueva"
    ,description = "Genera una nueva boleta de transacción de compra-venta entre clientes, incluyendo detalles como monto total, fecha y descripción de la transacción.")    
    public ResponseEntity<Boleta> generarBoleta(@RequestBody Boleta boleta) {
     try {
            return ResponseEntity.ok(service.generarBoleta(boleta));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }    
    @GetMapping("/{id}")
    @Operation(summary = "Busca una boleta especifica por su ID"
        ,description = "Obtiene una boleta específica por su ID, proporcionando detalles de la transacción, como monto total, fecha y descripción de la compra-venta.")
    public ResponseEntity<Boleta> obtenerBoletaPorId(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(service.obtenerBoletaPorId(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    @Operation(summary = "Lista todas las boletas"
        ,description = "Obtiene todas las boletas registradas, proporcionando una lista completa de transacciones de compra-venta, incluyendo detalles como monto total, fecha y descripción de cada boleta.")
    public ResponseEntity<java.util.List<Boleta>> obtenerTodasLasBoletas() {
        List<Boleta> boletas = service.obtenerTodasLasBoletas();
        if (boletas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(boletas);
    }
}