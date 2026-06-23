package com.multigacha.contactos.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;

import com.multigacha.contactos.model.Contacto;
import com.multigacha.contactos.service.ContactoService;

@WebMvcTest(ContactoController.class)//levanta solo la capa web, no la bd

public class ContactosControllerService {

    @Autowired 
    private MockMvc mock; //mock que simula las peticiones http

    @MockitoBean
    private ContactoService service; //service falso

    private Contacto contacto;

    @BeforeEach
    void setUp() {
        contacto = new Contacto();
        contacto.setId(1);
        contacto.setTelefono(123456789);
        contacto.setDireccion("Calle Falsa 123");
    }

    @Test
    void listarContactos_Retorna200() throws Exception {
        List<Contacto> contactos = new ArrayList<>();
        contactos.add(contacto);

        when(service.listarContactos()).thenReturn(contactos);

        mock.perform(get("/api/v1/contactos/lista"))
            .andExpect(status().isOk());
    }

    @Test
    void listarContactos_Retorna204() throws Exception {
        when(service.listarContactos()).thenReturn(new ArrayList<Contacto>());

        mock.perform(get("/api/v1/contactos/lista"))
            .andExpect(status().isNoContent());
    }

    @Test
    void buscarContacto_Retorna200() throws Exception {
        when(service.getContacto(1)).thenReturn(contacto);

        mock.perform(get("/api/v1/contactos/1"))
            .andExpect(status().isOk());
    }

    @Test
    void buscarContacto_Retorna404() throws Exception {
        when(service.getContacto(1)).thenThrow(new RuntimeException("Contacto no encontrado"));

        mock.perform(get("/api/v1/contactos/1"))
            .andExpect(status().isNotFound());
    }

    @Test
    void agregarContacto_Retorna200() throws Exception {
        when(service.addContacto(contacto)).thenReturn(contacto);

        mock.perform(post("/api/v1/contactos/add")
            .contentType("application/json")
            .content("{\"telefono\":123456789,\"direccion\":\"Calle Falsa 123\"}"))
            .andExpect(status().isOk());
    }

    @Test
    void agregarContacto_Retorna404() throws Exception {
        when(service.addContacto(any(Contacto.class))).thenThrow(new RuntimeException("Error al agregar contacto"));

        mock.perform(post("/api/v1/contactos/add")
            .contentType("application/json")
            .content("{\"telefono\":123456789,\"direccion\":\"Calle Falsa 123\"}"))
            .andExpect(status().isNotFound());
    }

    @Test
    void actualizarContacto_Retorna200() throws Exception {
        when(service.actualizarContacto(any(Contacto.class))).thenReturn(contacto);

        mock.perform(put("/api/v1/contactos/updt")
            .contentType("application/json")
            .content("{\"id\":1,\"telefono\":123456789,\"direccion\":\"Calle Falsa 123\"}"))
            .andExpect(status().isOk());
    }

    @Test
    void actualizarContacto_Retorna404() throws Exception {
        when(service.actualizarContacto(any(Contacto.class))).thenThrow(new RuntimeException("Error al actualizar contacto"));

        mock.perform(put("/api/v1/contactos/updt")
            .contentType("application/json")
            .content("{\"id\":1,\"telefono\":123456789,\"direccion\":\"Calle Falsa 123\"}"))
            .andExpect(status().isNotFound());
    }

    @Test
    void eliminarContacto_Retorna200() throws Exception {
        mock.perform(delete("/api/v1/contactos/eliminar/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    void eliminarContacto_Retorna404() throws Exception {
        doThrow(new RuntimeException("Error al eliminar contacto"))
        .when(service).deleteContacto(1);

        mock.perform(delete("/api/v1/contactos/eliminar/1"))
            .andExpect(status().isNotFound());
    }
    
}
