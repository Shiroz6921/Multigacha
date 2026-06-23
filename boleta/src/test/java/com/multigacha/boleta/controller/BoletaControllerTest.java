package com.multigacha.boleta.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import com.multigacha.boleta.model.Boleta;
import com.multigacha.boleta.service.BoletaService;

@WebMvcTest(BoletaController.class)

public class BoletaControllerTest {
    @Autowired
    private MockMvc mock; //mock que simula las peticiones http
    
    @MockitoBean
    private BoletaService service; //service falso

    private Boleta boleta;

    @BeforeEach
    void setUp() {
        boleta = new Boleta();
        boleta.setId(1);
        boleta.setIdComprador(2);
        boleta.setIdComprador(null);
        boleta.setDetalle("Compra de productos");
        boleta.setFecha(new java.util.Date());
        boleta.setMontoTotal(1000.00);
    }

    @Test
    void boleta_Retorna200() throws Exception {
    when(service.generarBoleta(boleta)).thenReturn(boleta);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(boleta);

        mock.perform(post("/api/v1/boletas/generar")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(status().isOk());
    }

    @Test
    void boleta_Retorna400() throws Exception {
        when(service.generarBoleta(any(Boleta.class)))
            .thenThrow(new RuntimeException("Error al generar la boleta"));
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(boleta);

        mock.perform(post("/api/v1/boletas/generar")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(status().isBadRequest());    
    }

    @Test
    void obtenerBoletaPorId_Retorna200() throws Exception {
        when(service.obtenerBoletaPorId(1)).thenReturn(boleta);

        mock.perform(get("/api/v1/boletas/1"))
            .andExpect(status().isOk());
    }

    @Test
    void obtenerBoletaPorId_Retorna404() throws Exception {
        when(service.obtenerBoletaPorId(1))
            .thenThrow(new RuntimeException("Boleta no encontrada"));

        mock.perform(get("/api/v1/boletas/1"))
            .andExpect(status().isNotFound());
    }

    @Test
    void obtenerTodasLasBoletas_Retorna200() throws Exception {
        List<Boleta> boletas = new ArrayList<>();
        boletas.add(boleta);
        when(service.obtenerTodasLasBoletas())
            .thenReturn(boletas);

        mock.perform(get("/api/v1/boletas"))
            .andExpect(status().isOk());
    }

    @Test
    void obtenerTodasLasBoletas_Retorna204() throws Exception {
        when(service.obtenerTodasLasBoletas())
            .thenReturn(new ArrayList<Boleta>());

        mock.perform(get("/api/v1/boletas"))
            .andExpect(status().isNoContent());
    }
}
