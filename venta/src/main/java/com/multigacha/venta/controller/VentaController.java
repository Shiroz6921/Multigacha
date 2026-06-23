package com.multigacha.venta.controller;

import com.multigacha.venta.model.Venta;
import com.multigacha.venta.service.VentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ventas")
@Tag(name = "Ventas", description = "Gestión de mercado y publicación de cartas")
public class VentaController {

    @Autowired
    private VentaService service;

    @PostMapping("/publicar")
    @Operation(summary = "Publicar una carta en el mercado")
    public ResponseEntity<?> publicarCarta(@RequestBody Venta venta) {
        try {
            Venta nuevaVenta = service.publicarCarta(venta);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaVenta);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener detalles de una publicación")
    public ResponseEntity<?> obtenerVenta(@PathVariable Integer id) {
        try {
            Venta venta = service.obtenerVentaPorId(id);
            return ResponseEntity.ok(venta);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/vender")
    @Operation(summary = "Marcar una carta publicada como VENDIDA")
    public ResponseEntity<?> marcarComoVendida(@PathVariable Integer id) {
        try {
            service.marcarComoVendida(id);
            return ResponseEntity.ok("Venta concretada con éxito");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}