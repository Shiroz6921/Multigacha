package com.multigacha.modificacion.controller;

import static org.mockito.ArgumentMatchers.any;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.multigacha.modificacion.model.Empleado;
import com.multigacha.modificacion.model.Modificacion;
import com.multigacha.modificacion.service.ModificacionService;

import tools.jackson.databind.ObjectMapper;

@WebMvcTest(Controller.class)
public class ModificacionControllerTest {

    @Autowired
    private MockMvc mock;

    @MockitoBean
    private ModificacionService service;

    private Empleado empleadoEjem;
    private Modificacion modificacionEjem;

    @BeforeEach
    public void setUp() {
        empleadoEjem = new Empleado();
        empleadoEjem.setId(1);
        empleadoEjem.setNombre("Juan");
        empleadoEjem.setApellido("Pérez");

        modificacionEjem = new Modificacion();
        modificacionEjem.setId(1);
        modificacionEjem.setFecha(new Date());
        modificacionEjem.setEmpleado(empleadoEjem);
        modificacionEjem.setIdProducto(1);
    }

    @Test
    void listarEmpleados_Retorna200() throws Exception {
        when(service.listarEmpleados()).thenReturn(List.of(empleadoEjem));

        mock.perform(get("/api/v1/modificaciones/empleados"))
                .andExpect(status().isOk());
    }

    @Test
    void listarEmpleados_Retorna204() throws Exception {
        when(service.listarEmpleados()).thenReturn(List.of());

        mock.perform(get("/api/v1/modificaciones/empleados"))
                .andExpect(status().isNoContent());
    }

    @Test
    void crearEmpleado_Retorna201() throws Exception {
        when(service.crearEmpleado(any(Empleado.class))).thenReturn(empleadoEjem);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(empleadoEjem);

        mock.perform(post("/api/v1/modificaciones/empleados")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    void crearEmpleado_Retorna400() throws Exception {
        when(service.crearEmpleado(any(Empleado.class)))
                .thenThrow(new RuntimeException("Error al crear empleado"));

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(empleadoEjem);

        mock.perform(post("/api/v1/modificaciones/empleados")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void listarModificaciones_Retorna200() throws Exception {
        when(service.listarModificaciones()).thenReturn(List.of(modificacionEjem));

        mock.perform(get("/api/v1/modificaciones"))
                .andExpect(status().isOk());
    }

    @Test
    void listarModificaciones_Retorna204() throws Exception {
        when(service.listarModificaciones()).thenReturn(List.of());

        mock.perform(get("/api/v1/modificaciones"))
                .andExpect(status().isNoContent());
    }

    @Test
    void listarPorProducto_Retorna200() throws Exception {
        when(service.mostrarModificacionesPorProducto(1)).thenReturn(List.of(modificacionEjem));

        mock.perform(get("/api/v1/modificaciones/producto/1"))
                .andExpect(status().isOk());
    }

    @Test
    void listarPorProducto_Retorna204() throws Exception {
        when(service.mostrarModificacionesPorProducto(99)).thenReturn(List.of());

        mock.perform(get("/api/v1/modificaciones/producto/99"))
                .andExpect(status().isNoContent());
    }

    @Test
    void listarPorEmpleado_Retorna200() throws Exception {
        when(service.listarModificacionesPorEmpleado(1)).thenReturn(List.of(modificacionEjem));

        mock.perform(get("/api/v1/modificaciones/empleado/1"))
                .andExpect(status().isOk());
    }

    @Test
    void listarPorEmpleado_Retorna404() throws Exception {
        when(service.listarModificacionesPorEmpleado(99))
                .thenThrow(new RuntimeException("Empleado no encontrado con ID: 99"));

        mock.perform(get("/api/v1/modificaciones/empleado/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void registrarModificacion_Retorna201() throws Exception {
        when(service.registrarModificacion(1, 1)).thenReturn(modificacionEjem);

        mock.perform(post("/api/v1/modificaciones")
                .param("idEmpleado", "1")
                .param("idProducto", "1"))
                .andExpect(status().isCreated());
    }

    @Test
    void registrarModificacion_Retorna400() throws Exception {
        when(service.registrarModificacion(anyInt(), anyInt()))
                .thenThrow(new RuntimeException("Empleado no encontrado con ID: 99"));

        mock.perform(post("/api/v1/modificaciones")
                .param("idEmpleado", "99")
                .param("idProducto", "1"))
                .andExpect(status().isBadRequest());
    }
}