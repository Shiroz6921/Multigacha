package com.multigacha.intercambio.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.multigacha.intercambio.client.ProductoClient;
import com.multigacha.intercambio.dto.IntercambioRequestDTO;
import com.multigacha.intercambio.model.Intercambio;
import com.multigacha.intercambio.model.ProductoCliente;
import com.multigacha.intercambio.repo.ProductoClienteRepo;
import com.multigacha.intercambio.service.IntercambioService;

import org.springframework.http.MediaType;

@WebMvcTest(IntercambioController.class)
public class IntercambioControllerTest {

    @Autowired
    private MockMvc mock;

    @MockitoBean
    private IntercambioService service;

    @MockitoBean
    private ProductoClienteRepo productoClienteRepo; // ✅ agrega esto

    @MockitoBean
    private ProductoCliente productoClient; // ✅ mantén esto también

    private Intercambio intercambioEjem;
    private IntercambioRequestDTO requestDTO;
    private ProductoCliente productoClienteEjem;

    @BeforeEach
    public void setUp() {
        productoClienteEjem = new ProductoCliente();
        productoClienteEjem.setId(1);
        productoClienteEjem.setIdCliente(1);
        productoClienteEjem.setIdProducto(1);

        intercambioEjem = new Intercambio();
        intercambioEjem.setId(1);
        intercambioEjem.setFecha(new Date());
        intercambioEjem.setTraspaso(productoClienteEjem);

        requestDTO = new IntercambioRequestDTO();
        requestDTO.setProductoA(1);
        requestDTO.setProductoB(2);
    }

    @Test
    void crearIntercambio_Retorna200() throws Exception {
        when(service.crearIntercambio(any(IntercambioRequestDTO.class))).thenReturn(intercambioEjem);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(requestDTO);

        mock.perform(put("/api/v1/intercambios/intercambiar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void crearIntercambio_Retorna400() throws Exception {
        when(service.crearIntercambio(any(IntercambioRequestDTO.class)))
                .thenThrow(new RuntimeException("Error al crear intercambio"));

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(requestDTO);

        mock.perform(put("/api/v1/intercambios/intercambiar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void eliminarIntercambio_Retorna204() throws Exception {
        doNothing().when(service).eliminarIntercambio(1);

        mock.perform(delete("/api/v1/intercambios/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void eliminarIntercambio_Retorna404() throws Exception {
        doThrow(new RuntimeException("Intercambio no encontrado")).when(service).eliminarIntercambio(99);

        mock.perform(delete("/api/v1/intercambios/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void listar_Retorna200() throws Exception {
        when(service.obtenerTodos()).thenReturn(List.of(intercambioEjem));

        mock.perform(get("/api/v1/intercambios/listar"))
                .andExpect(status().isOk());
    }

    @Test
    void listar_Retorna200Vacio() throws Exception {
        when(service.obtenerTodos()).thenReturn(List.of());

        mock.perform(get("/api/v1/intercambios/listar"))
                .andExpect(status().isOk());
    }

    @Test
    void generar_Retorna200() throws Exception {
        when(service.intercambio(any(ProductoCliente.class))).thenReturn(productoClienteEjem);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(productoClienteEjem);

        mock.perform(post("/api/v1/intercambios/generar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void generar_Retorna400() throws Exception {
        when(service.intercambio(any(ProductoCliente.class)))
                .thenThrow(new RuntimeException("Error al generar"));

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(productoClienteEjem);

        mock.perform(post("/api/v1/intercambios/generar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }
}