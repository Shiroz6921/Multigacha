package com.multigacha.compra.controller;

import com.multigacha.compra.dto.CompraBoletaDTO;
import com.multigacha.compra.model.Compra;
import com.multigacha.compra.service.CompraService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/compras")
@Tag(name = "Compras", description = "Operacion Compras")
public class CompraController {

    @Autowired
    private CompraService service;

    @PostMapping("/carrito/{idComprador}/{idCarrito}")
    @Operation(summary = "Procesa la compra de un carrito específico para un cliente identificado por su ID, generando una boleta de transacción con los detalles de la compra, incluyendo el monto total, fecha y descripción de la transacción.")
    public ResponseEntity<CompraBoletaDTO> procesarCarrito(@PathVariable Integer idComprador, @PathVariable Integer idCarrito) {
        return ResponseEntity.ok(service.procesarCompraDesdeCarrito(idComprador, idCarrito));
    }
    
    @GetMapping
    @Operation(summary = "Lista todas las compras realizadas en el sistema.", description = "Retorna una lista de objetos Compra con los detalles de cada compra realizada. Si no hay compras, retorna una respuesta sin contenido.")
    public List<Compra> todo (){
        return service.listar();
    }
}