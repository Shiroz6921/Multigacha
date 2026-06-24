package com.multigacha.carrito.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import com.multigacha.carrito.dto.CarritoDTO;
import com.multigacha.carrito.model.Carrito;
import com.multigacha.carrito.service.CarritoService;

@WebMvcTest(CarritoController.class)
public class CarritoControllerTest {

    @Autowired
    private MockMvc mock;

    @MockitoBean
    private CarritoService service;

    private Carrito carritoEjem;
    private CarritoDTO carritoDTO;

    @BeforeEach
    public void setUp() {
        carritoEjem = new Carrito();
        carritoEjem.setId(1);
        carritoEjem.setClienteId(1);
        carritoEjem.setTotal(2000.0);

        carritoDTO = new CarritoDTO();
        carritoDTO.setProductoId(1);
        carritoDTO.setCantidad(2);
    }

    @Test
    void crear_Retorna200() throws Exception {
        when(service.crearCarrito(1)).thenReturn(carritoEjem);

        mock.perform(post("/api/v1/carrito/1/crear"))
                .andExpect(status().isOk());
    }

    @Test
    void crear_Retorna400() throws Exception {
        when(service.crearCarrito(99)).thenThrow(new RuntimeException("Cliente no encontrado"));

        mock.perform(post("/api/v1/carrito/99/crear"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void agregar_Retorna200() throws Exception {
        when(service.agregarProducto(anyInt(), any(CarritoDTO.class))).thenReturn(carritoEjem);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(carritoDTO);

        mock.perform(post("/api/v1/carrito/1/agregar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void agregar_Retorna404() throws Exception {
        when(service.agregarProducto(anyInt(), any(CarritoDTO.class)))
                .thenThrow(new RuntimeException("Carrito no encontrado"));

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(carritoDTO);

        mock.perform(post("/api/v1/carrito/99/agregar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    void listar_Retorna200() throws Exception {
        when(service.listarTodos()).thenReturn(List.of(carritoEjem));

        mock.perform(get("/api/v1/carrito"))
                .andExpect(status().isOk());
    }

    @Test
    void listar_Retorna204() throws Exception {
        when(service.listarTodos()).thenReturn(List.of());

        mock.perform(get("/api/v1/carrito"))
                .andExpect(status().isNoContent());
    }

    @Test
    void buscarPorId_Retorna200() throws Exception {
        when(service.buscarId(1)).thenReturn(carritoEjem);

        mock.perform(get("/api/v1/carrito/1"))
                .andExpect(status().isOk());
    }

    @Test
    void buscarPorId_Retorna404() throws Exception {
        when(service.buscarId(99)).thenThrow(new RuntimeException("Carrito no encontrado"));

        mock.perform(get("/api/v1/carrito/99"))
                .andExpect(status().isNotFound());
    }
}