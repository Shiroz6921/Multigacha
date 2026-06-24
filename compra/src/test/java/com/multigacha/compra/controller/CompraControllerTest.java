package com.multigacha.compra.controller;
import static org.mockito.ArgumentMatchers.anyInt;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.multigacha.compra.dto.BoletaDTO;
import com.multigacha.compra.dto.CompraBoletaDTO;
import com.multigacha.compra.model.Compra;
import com.multigacha.compra.service.CompraService;

@WebMvcTest(CompraController.class)
public class CompraControllerTest {

    @Autowired
    private MockMvc mock;

    @MockitoBean
    private CompraService service;

    private Compra compraEjem;
    private CompraBoletaDTO compraBoletaEjem;
    private BoletaDTO boletaEjem;

    @BeforeEach
    public void setUp() {
        compraEjem = new Compra();
        compraEjem.setId(1);
        compraEjem.setIdComprador(1);
        compraEjem.setIdVenta(1);
        compraEjem.setTotalPagado(2000.0);
        compraEjem.setFechaCompra(new Date());

        boletaEjem = new BoletaDTO();
        boletaEjem.setIdComprador(1);
        boletaEjem.setIdVendedor(0);
        boletaEjem.setMontoTotal(2000.0);
        boletaEjem.setDetalle("Compra desde Carrito ID: 1 con 1 productos distintos.");

        compraBoletaEjem = new CompraBoletaDTO(compraEjem, boletaEjem);
    }

    @Test
    void procesarCarrito_Retorna200() throws Exception {
        when(service.procesarCompraDesdeCarrito(1, 1)).thenReturn(compraBoletaEjem);

        mock.perform(post("/api/v1/compras/carrito/1/1"))
                .andExpect(status().isOk());
    }

    @Test
    void procesarCarrito_Retorna400() throws Exception {
        when(service.procesarCompraDesdeCarrito(anyInt(), anyInt()))
                .thenThrow(new RuntimeException("El carrito está vacío."));

        mock.perform(post("/api/v1/compras/carrito/1/99"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void listar_Retorna200() throws Exception {
        when(service.listar()).thenReturn(List.of(compraEjem));

        mock.perform(get("/api/v1/compras"))
                .andExpect(status().isOk());
    }

    @Test
    void listar_Retorna204() throws Exception {
        when(service.listar()).thenReturn(List.of());

        mock.perform(get("/api/v1/compras"))
                .andExpect(status().isNoContent());
    }
}