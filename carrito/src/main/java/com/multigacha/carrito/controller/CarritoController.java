package com.multigacha.carrito.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.multigacha.carrito.dto.CarritoDTO;
import com.multigacha.carrito.model.Carrito;
import com.multigacha.carrito.model.ProductosCarritos;
import com.multigacha.carrito.service.CarritoService;

@RestController
@RequestMapping("/api/v1/carrito")
public class CarritoController {

    @Autowired
    private CarritoService service;

    @PostMapping("/{id}/crear")
    public ResponseEntity<Carrito> crear(@PathVariable Integer id) {
        return ResponseEntity.ok(service.crearCarrito(id));
    }

    @PostMapping("/{id}/agregar")
    public ResponseEntity<?> agregar(@PathVariable Integer id, @RequestBody CarritoDTO dto) {
        try {
            return ResponseEntity.ok(service.agregarProducto(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }    
    }

    @GetMapping("/todo")
    public ResponseEntity<List<ProductosCarritos>> listarTodo() {
        List<ProductosCarritos> lista = service.obtenerTodo();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(lista);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductosCarritos> buscarProductoPorId(@PathVariable Integer id) {
        try{
            return ResponseEntity.ok(service.buscarPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    
    }  
    @GetMapping("/carrito/{carritoId}")
    public ResponseEntity<List<ProductosCarritos>> buscarProductosPorCarritoId(@PathVariable Integer carritoId) {
        List<ProductosCarritos> productos = service.buscarCarrito(carritoId);
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }
}
