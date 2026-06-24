package com.multigacha.venta.controller;

import com.multigacha.venta.model.Venta;
import com.multigacha.venta.service.VentaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VentaController.class)
public class VentaControllerTest {

    @Autowired
    private MockMvc mock;

    @MockitoBean
    private VentaService service;

    private Venta ventaEjem;

    @BeforeEach
    void setUp() {
        ventaEjem = new Venta();
        ventaEjem.setId(1);
        ventaEjem.setIdVendedor(10);
        ventaEjem.setIdProducto(100);
        ventaEjem.setPrecio(15000.0);
        ventaEjem.setEstado("DISPONIBLE");
    }

    @Test
    void publicarCarta_Retorna201_Creado() throws Exception {
        when(service.publicarCarta(any(Venta.class))).thenReturn(ventaEjem);

        mock.perform(post("/api/v1/ventas/publicar")
                .contentType("application/json")
                .content("{\"idVendedor\":10,\"idProducto\":100,\"precio\":15000.0}"))
                .andExpect(status().isCreated());
    }

    @Test
    void publicarCarta_Error_Retorna400_BadRequest() throws Exception {
        when(service.publicarCarta(any(Venta.class)))
                .thenThrow(new RuntimeException("Error: El jugador no posee esta carta"));

        mock.perform(post("/api/v1/ventas/publicar")
                .contentType("application/json")
                .content("{\"idVendedor\":10,\"idProducto\":999,\"precio\":15000.0}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void obtenerVenta_Retorna200_Ok() throws Exception {
        when(service.obtenerVentaPorId(1)).thenReturn(ventaEjem);

        mock.perform(get("/api/v1/ventas/1"))
                .andExpect(status().isOk());
    }

    @Test
    void obtenerVenta_NoEncontrada_Retorna404_NotFound() throws Exception {
        when(service.obtenerVentaPorId(99))
                .thenThrow(new RuntimeException("Publicación no encontrada"));

        mock.perform(get("/api/v1/ventas/99"))
                .andExpect(status().isNotFound());
    }



    @Test
    void marcarVendida_Retorna200_Ok() throws Exception {
        doNothing().when(service).marcarComoVendida(1);

        mock.perform(put("/api/v1/ventas/1/vender"))
                .andExpect(status().isOk());
    }

    @Test
    void marcarVendida_NoEncontrada_Retorna404_NotFound() throws Exception {
        doThrow(new RuntimeException("Publicación no encontrada"))
                .when(service).marcarComoVendida(99);

        mock.perform(put("/api/v1/ventas/99/vender"))
                .andExpect(status().isNotFound());
    }
}