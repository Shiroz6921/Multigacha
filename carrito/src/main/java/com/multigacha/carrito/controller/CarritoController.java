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

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/carrito")
@Tag(name = "Carrito", description = "Operacion Carrito")
public class CarritoController {

    @Autowired
    private CarritoService service;

    @PostMapping("/{id}/crear")
    @Schema(description = "Crea un nuevo carrito para un cliente específico, identificado por su ID. Retorna el carrito creado con su ID asignado.")
    public ResponseEntity<Carrito> crear(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(service.crearCarrito(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }    

    @PostMapping("/{id}/agregar")
    @Schema(description = "Agrega un producto al carrito de un cliente específico, identificado por su ID. Retorna el carrito actualizado.")
    public ResponseEntity<Carrito> agregar(@PathVariable Integer id, @RequestBody CarritoDTO dto) {
        try {
            return ResponseEntity.ok(service.agregarProducto(id, dto));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }    
    @GetMapping
    @Schema(description = "Lista todos los carritos disponibles.")  
    public ResponseEntity<List<Carrito>> listar() {
        List<Carrito> carrito = service.listarTodos();
        if (carrito.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(carrito);
    }

    @GetMapping("/{id}")
    @Schema(description = "Busca un carrito por su ID.")
    public ResponseEntity<Carrito> buscarPorId(@PathVariable Integer id){
        try {
            return ResponseEntity.ok(service.buscarId(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }  
}