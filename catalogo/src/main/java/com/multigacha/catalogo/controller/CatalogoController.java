package com.multigacha.catalogo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.multigacha.catalogo.service.CatalogoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.multigacha.catalogo.dto.ProductoDTO;
import com.multigacha.catalogo.model.Categoria;
import com.multigacha.catalogo.model.Producto;

@RestController
@RequestMapping("/api/v1/catalogo")
@Tag(name = "Catalogo", description = "Operacion Catalogo")
public class CatalogoController {
    @Autowired
    private CatalogoService service;

    @PostMapping("/expansion")
    @Operation  (summary    = "Crea una nueva expansion en el catalogo.",
                description = "Recibe un objeto Categoria con los detalles de la expansion a agregar, y retorna la expansion agregada con su ID asignado.")
    public ResponseEntity<Categoria> crearExpansion(@RequestBody Categoria categoria) {
        try {
            return ResponseEntity.ok(service.guardarExpansion(categoria));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/carta")
    @Operation  (summary    = "Agrega una nueva carta al catalogo.",
                description = "Recibe un objeto ProductoDTO con los detalles de la carta a agregar, y retorna el producto agregado con su ID asignado.")
    public ResponseEntity<Producto> agregarCarta(@RequestBody ProductoDTO dto) {
        try {
            Producto producto = service.agregarCarta(dto);
            return ResponseEntity.ok(producto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/todo")
    @Operation  (summary    = "Lista todas las categorias y productos del catalogo.",
                description = "Retorna una lista de todas las categorias y sus productos asociados en el catalogo.")
    public ResponseEntity<List<Categoria>> listarTodo() {
        List<Categoria> lista = service.obtenerTodo();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(lista);
        }
    }

    @GetMapping("/{id}")
    @Operation  (summary    = "Busca un producto del catalogo por ID.",
                description = "Retorna un producto segun el id proporcionado.")
    public ResponseEntity<Producto> buscarProductoPorId(@PathVariable Integer id) {
        try{
            return ResponseEntity.ok(service.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    
    }  

    @PutMapping("/{id}/reducir-stock/{cantidad}")
    @Operation  (summary    = "Reduce el stock de un producto.",
                description = "Recibe el ID del producto y la cantidad a reducir, y actualiza el stock del producto en consecuencia.")
    public ResponseEntity<Producto> reducirStock(@PathVariable Integer id, @PathVariable Integer cantidad){
        try{
            return ResponseEntity.ok(service.bajarStock(id, cantidad));
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

}