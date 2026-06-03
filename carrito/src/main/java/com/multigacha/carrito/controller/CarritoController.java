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
    public ResponseEntity<Carrito> agregar(@PathVariable Integer id, @RequestBody CarritoDTO dto) {
        return ResponseEntity.ok(service.agregarProducto(id, dto));
    }
    @GetMapping
    public ResponseEntity<List<Carrito>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carrito> buscarPorId(Integer id){
        return ResponseEntity.ok(service.buscarId(id));
    }
}