package com.multigacha.clientes.controller;

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

import com.multigacha.clientes.dto.ClienteDTO;
import com.multigacha.clientes.model.Cliente;
import com.multigacha.clientes.service.ClienteService;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {
    @Autowired
    private ClienteService servicio;

    @GetMapping
    public ResponseEntity<List<Cliente>> listar() {
        if (servicio.mostrarClientes().isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(servicio.mostrarClientes());
        }
    }

    @PostMapping
    public ResponseEntity<Cliente> crear (@RequestBody ClienteDTO dto) {
        return ResponseEntity.ok(servicio.clienteMasInventario(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            servicio.borrarClientePorId(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(servicio.mostrarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Cliente> actualizar(@PathVariable Integer id, @RequestBody ClienteDTO nuevo) {
        try {
            return ResponseEntity.ok(servicio.modificarCliente(id, nuevo));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
