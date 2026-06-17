package com.multigacha.venta.controller;

import com.multigacha.venta.model.Venta;
import com.multigacha.venta.service.VentaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ventas")
@Tag(name = "Ventas", description = "Operacion Ventas")
public class VentaController {
    @Autowired
    private VentaService service;

    @PostMapping("/publicar")
    @Operation(summary = "Publica un producto para la venta.", description = "Recibe un objeto Venta con los detalles de la publicación, incluyendo el ID del vendedor, el ID del producto y el precio, y retorna la publicación de venta creada con su ID asignado.")  
    public ResponseEntity<Venta> publicarCarta(@RequestBody Venta venta) {
        return ResponseEntity.ok(service.publicarCarta(venta));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene los detalles de una publicación de venta por su ID.", description = "Recibe el ID de la publicación de venta y retorna los detalles de la publicación, incluyendo el ID del vendedor, el ID del producto, el precio y el estado de la publicación.")
    public ResponseEntity<Venta> obtenerVenta(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtenerVentaPorId(id));
    }

    @PutMapping("/{id}/vender")
    @Operation(summary = "Marca una publicación de venta como vendida.", description = "Recibe el ID de la publicación de venta y la marca como vendida.")
    public ResponseEntity<Void> marcarVendida(@PathVariable Integer id) {
        service.marcarComoVendida(id);
        return ResponseEntity.ok().build();
    }
}