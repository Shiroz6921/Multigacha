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
import com.multigacha.catalogo.dto.ProductoDTO;
import com.multigacha.catalogo.model.Categoria;
import com.multigacha.catalogo.model.Producto;

@RestController
@RequestMapping("/api/v1/catalogo")
public class CatalogoController {
    @Autowired
    private CatalogoService service;

    @PostMapping("/expansion")
    public ResponseEntity<Categoria> crearExpansion(@RequestBody Categoria categoria) {
        try {
            return ResponseEntity.ok(service.guardarExpansion(categoria));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/carta")
    public ResponseEntity<Producto> agregarCarta(@RequestBody ProductoDTO dto) {
        try {
            Producto producto = service.agregarCarta(dto);
            return ResponseEntity.ok(producto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/todo")
    public ResponseEntity<List<Categoria>> listarTodo() {
        List<Categoria> lista = service.obtenerTodo();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(lista);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscarProductoPorId(@PathVariable Integer id) {
        try{
            return ResponseEntity.ok(service.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    
    }  

    @PutMapping("/{id}/reducir-stock/{cantidad}")
    public ResponseEntity<Producto> reducirStock(@PathVariable Integer id, @PathVariable Integer cantidad){
        try{
            return ResponseEntity.ok(service.bajarStock(id, cantidad));
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

}