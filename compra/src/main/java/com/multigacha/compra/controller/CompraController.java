package com.multigacha.compra.controller;

import com.multigacha.compra.dto.CompraBoletaDTO;
import com.multigacha.compra.model.Compra;
import com.multigacha.compra.service.CompraService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/compras")
public class CompraController {

    @Autowired
    private CompraService service;

    @PostMapping("/carrito/{idComprador}/{idCarrito}")
    public ResponseEntity<CompraBoletaDTO> procesarCarrito(@PathVariable Integer idComprador, @PathVariable Integer idCarrito) {
        return ResponseEntity.ok(service.procesarCompraDesdeCarrito(idComprador, idCarrito));
    }
    
    @GetMapping
    public List<Compra> todo (){
        return service.listar();
    }
}