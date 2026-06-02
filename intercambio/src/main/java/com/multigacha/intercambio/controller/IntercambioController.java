package com.multigacha.intercambio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.multigacha.intercambio.model.Intercambio;
import com.multigacha.intercambio.model.ProductoCliente;
import com.multigacha.intercambio.service.IntercambioService;

@RestController
@RequestMapping("/api/v1/intercambios")
public class IntercambioController {
    @Autowired
    private IntercambioService service;

    @GetMapping
    public ResponseEntity<List<Intercambio>> listarIntercambios() {
        List<Intercambio> lista = service.listarIntercambios();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(lista);
        }
    }

    @GetMapping("/inventarios")
    public ResponseEntity<List<ProductoCliente>> listarInventarios() {
        List<ProductoCliente> lista = service.listarInventarios();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(lista);
        }
    }

    @GetMapping("/inventarios/cliente/{id}")
    public ResponseEntity<List<ProductoCliente>> listarInventariosPorCliente(@PathVariable Integer id) {
        List<ProductoCliente> lista = service.listarInventariosPorCliente(id);
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(lista);
        }
    }

    @GetMapping("/inventarios/producto/{id}")
    public ResponseEntity<ProductoCliente> getInventarioPorProducto(@PathVariable Integer id) {
        try {
            ProductoCliente inventario = service.mostrarInventarioPorProducto(id);
            return ResponseEntity.ok(inventario);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/inventarios/{id}")
    public ResponseEntity<ProductoCliente> getInventario(@PathVariable Integer id) {
        try {
            ProductoCliente inventario = service.buscarInventarioPorId(id);
            return ResponseEntity.ok(inventario);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/inventarios")
    public ResponseEntity<ProductoCliente> crearInventario(@RequestBody Integer idCliente,
            @RequestBody Integer idProducto) {
        try {
            ProductoCliente inventario = service.crearInventario(idCliente, idProducto);
            return ResponseEntity.ok(inventario);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/intercambiar")
    public ResponseEntity<Intercambio> crearIntercambio(@RequestBody Integer idProducto,
            @RequestBody Integer idCliente1, @RequestBody Integer idCliente2) {
        try {
            Intercambio intercambio = service.crearIntercambio(idProducto, idCliente1, idCliente2);
            return ResponseEntity.ok(intercambio);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarIntercambio(@PathVariable Integer id) {
        try {
            service.eliminarIntercambio(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/inventarios/{id}")
    public ResponseEntity<Void> eliminarInventario(@PathVariable Integer id) {
        try {
            service.eliminarInventario(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
