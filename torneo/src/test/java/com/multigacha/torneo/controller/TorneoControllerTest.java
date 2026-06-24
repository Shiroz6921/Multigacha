package com.multigacha.torneo.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;

import java.util.ArrayList;
import java.util.List;

import com.multigacha.torneo.dto.ClienteDTO;
import com.multigacha.torneo.model.inscripciones;
import com.multigacha.torneo.model.torneo;
import com.multigacha.torneo.service.TorneoService;

@WebMvcTest(TorneoController.class)
public class TorneoControllerTest {

    @Autowired
    private MockMvc mock;

    @MockitoBean
    private TorneoService service;

    private torneo torneoEjem;
    private inscripciones inscripcionEjem;

    @BeforeEach
    void setUp() {
        torneoEjem = new torneo();
        torneoEjem.setId(1);
        torneoEjem.setNombre("Pokemon Championship");
        torneoEjem.setMaxParticipantes(16);

        inscripcionEjem = new inscripciones();
        inscripcionEjem.setId(10);
        inscripcionEjem.setClienteId(100);
        inscripcionEjem.setNombre("Ash Ketchum");
        inscripcionEjem.setTorneo(torneoEjem);
    }


    @Test
    void crearTorneo_Retorna200() throws Exception {
        when(service.crearTorneo(any(torneo.class))).thenReturn(torneoEjem);

        mock.perform(post("/api/v1/torneo/crear")
            .contentType("application/json")
            .content("{\"nombre\":\"Pokemon Championship\",\"maxParticipantes\":16}"))
            .andExpect(status().isOk());
    }

    @Test
    void crearTorneo_Retorna404() throws Exception {
        when(service.crearTorneo(any(torneo.class))).thenThrow(new RuntimeException("Error genérico"));

        mock.perform(post("/api/v1/torneo/crear")
            .contentType("application/json")
            .content("{\"nombre\":\"Pokemon Championship\",\"maxParticipantes\":16}"))
            .andExpect(status().isNotFound());
    }


    @Test
    void inscripcion_Retorna200() throws Exception {
        when(service.agregarGente(eq(1), any(ClienteDTO.class))).thenReturn(inscripcionEjem);

        mock.perform(post("/api/v1/torneo/inscripcion/1")
            .contentType("application/json")
            .content("{\"id\":100,\"nombre\":\"Ash Ketchum\"}"))
            .andExpect(status().isOk());
    }

    @Test
    void inscripcion_Retorna400() throws Exception {
        when(service.agregarGente(eq(1), any(ClienteDTO.class)))
            .thenThrow(new RuntimeException("Esta persona ya se encuentra inscrita"));

        mock.perform(post("/api/v1/torneo/inscripcion/1")
            .contentType("application/json")
            .content("{\"id\":100,\"nombre\":\"Ash Ketchum\"}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void inscripcion_PersonaNoExiste_Retorna400() throws Exception {
        when(service.agregarGente(eq(1), any(ClienteDTO.class)))
            .thenThrow(new RuntimeException("Cliente no encontrado"));

        mock.perform(post("/api/v1/torneo/inscripcion/1")
            .contentType("application/json")
            .content("{\"id\":999,\"nombre\":\"Usuario Inexistente\"}"))
            .andExpect(status().isBadRequest());
    }


    @Test
    void listarTodo_Retorna200() throws Exception {
        List<inscripciones> lista = new ArrayList<>();
        lista.add(inscripcionEjem);

        when(service.obtenerTodo()).thenReturn(lista);

        mock.perform(get("/api/v1/torneo/todo"))
            .andExpect(status().isOk());
    }

    @Test
    void listarTodo_Retorna204() throws Exception {
        when(service.obtenerTodo()).thenReturn(new ArrayList<>());

        mock.perform(get("/api/v1/torneo/todo"))
            .andExpect(status().isNoContent());
    }
    
    
    @Test
    void eliminarTorneo_Retorna204() throws Exception {
        mock.perform(delete("/api/v1/torneo/eliminar/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    void eliminarTorneo_Retorna404() throws Exception {
        doThrow(new RuntimeException("Torneo no encontrado"))
        .when(service).borrarTorneo(1);

        mock.perform(delete("/api/v1/torneo/eliminar/1"))
            .andExpect(status().isNotFound());
    }
}