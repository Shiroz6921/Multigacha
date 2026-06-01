package com.multigacha.venta.controller;

import com.multigacha.venta.model.Venta;
import com.multigacha.venta.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ventas")
public class VentaController {
    @Autowired
    private VentaService service;

    @PostMapping("/publicar")
    public ResponseEntity<Venta> publicarCarta(@RequestBody Venta venta) {
        return ResponseEntity.ok(service.publicarCarta(venta));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venta> obtenerVenta(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtenerVentaPorId(id));
    }

    @PutMapping("/{id}/vender")
    public ResponseEntity<Void> marcarVendida(@PathVariable Integer id) {
        service.marcarComoVendida(id);
        return ResponseEntity.ok().build();
    }
}