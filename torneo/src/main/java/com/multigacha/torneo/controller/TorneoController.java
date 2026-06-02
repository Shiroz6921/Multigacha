package com.multigacha.torneo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.multigacha.torneo.dto.ClienteDTO;
import com.multigacha.torneo.model.inscripciones;
import com.multigacha.torneo.model.torneo;
import com.multigacha.torneo.service.TorneoService;

@RestController
@RequestMapping("/api/v1/torneo")
public class TorneoController {
    @Autowired
    private TorneoService service;

    @PostMapping("/crear")
    public ResponseEntity<torneo> crearTorneo(@RequestBody torneo torneo) {
        try {
            return ResponseEntity.ok(service.crearTorneo(torneo));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/inscripcion/{torneoId}")
    public ResponseEntity<?> inscripcion(@PathVariable Integer torneoId,@RequestBody ClienteDTO dto) {
        try {
            inscripciones inscripciones = service.agregarGente(torneoId,dto);
            return ResponseEntity.ok(inscripciones);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/todo")
    public ResponseEntity<List<inscripciones>> listarTodo() {
        List<inscripciones> lista = service.obtenerTodo();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(lista);
        }
    }
}
