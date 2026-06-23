package com.multigacha.torneo.controller;

import java.util.List;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/torneo")
@Tag(name = "Torneo", description = "Operacion Torneo")
public class TorneoController {
    @Autowired
    private TorneoService service;

    @PostMapping("/crear")
    @Operation(summary = "Crea un nuevo torneo con un nombre y un número máximo de participantes especificados. Retorna el torneo creado con su ID asignado.")
    public ResponseEntity<torneo> crearTorneo(@RequestBody torneo torneo) {
        try {
            return ResponseEntity.ok(service.crearTorneo(torneo));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/inscripcion/{torneoId}")
    @Operation(summary = "Inscribe a un cliente en un torneo específico, identificado por su ID. Recibe un objeto ClienteDTO con los detalles del cliente a inscribir, y retorna la inscripción creada con su ID asignado.")
    public ResponseEntity<?> inscripcion(@PathVariable Integer torneoId,@RequestBody ClienteDTO dto) {
        try {
            inscripciones inscripciones = service.agregarGente(torneoId,dto);
            return ResponseEntity.ok(inscripciones);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/todo")
    @Operation(summary = "Lista todas las inscripciones registradas en el sistema, incluyendo detalles como el torneo al que se inscribieron, el ID del cliente y el nombre del participante.")
    public ResponseEntity<List<inscripciones>> listarTodo() {
        List<inscripciones> lista = service.obtenerTodo();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(lista);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    @Operation(summary = "Elimina un torneo por su ID.")
    public ResponseEntity<?> eliminarTorneo(@PathVariable Integer id) {
        try {
            service.borrarTorneo(id);
            return ResponseEntity.noContent().build(); 
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


}
