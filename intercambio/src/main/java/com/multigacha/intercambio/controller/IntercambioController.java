package com.multigacha.intercambio.controller;

import com.multigacha.intercambio.repo.ProductoClienteRepo;
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

import com.multigacha.intercambio.client.ProductoClient;
import com.multigacha.intercambio.dto.IntercambioRequestDTO;
import com.multigacha.intercambio.model.Intercambio;
import com.multigacha.intercambio.model.ProductoCliente;
import com.multigacha.intercambio.service.IntercambioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/intercambios")
@Tag(name = "Intercambio", description = "Operacion Intercambio")
public class IntercambioController {
    private final ProductoClienteRepo productoClienteRepo;
    @Autowired
    private IntercambioService service;

    IntercambioController(ProductoClienteRepo productoClienteRepo) {
        this.productoClienteRepo = productoClienteRepo;
    }

    @PutMapping("/intercambiar")
    @Operation(summary = "Crea un nuevo intercambio entre clientes.", description = "Recibe un objeto IntercambioRequestDTO con los detalles del intercambio a realizar, incluyendo los IDs de los productos y clientes involucrados, y retorna el intercambio creado con su ID asignado.")
    public ResponseEntity<Intercambio> crearIntercambio(@RequestBody IntercambioRequestDTO dto) {
        try {
            return ResponseEntity.ok(service.crearIntercambio(dto));
        } catch (RuntimeException e) {
        e.printStackTrace();
        return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un intercambio existente.", description = "Elimina un intercambio específico según su ID. Retorna una respuesta de éxito si la operación es exitosa.")
    public ResponseEntity<Void> eliminarIntercambio(@PathVariable Integer id) {
        try {
            service.eliminarIntercambio(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/listar")
    @Operation(summary = "Lista todos los intercambios.", description = "Retorna una lista con todos los intercambios registrados.")
    public ResponseEntity<List<Intercambio>> listarTodosLosIntercambios() {
        List<Intercambio> intercambios = service.obtenerTodos();
        return ResponseEntity.ok(intercambios);
    }

    @PostMapping("/generar")
    public ResponseEntity<ProductoCliente> crear(@RequestBody ProductoCliente productoCliente){
        try{
            return ResponseEntity.ok(service.intercambio(productoCliente));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }   
    }
}
