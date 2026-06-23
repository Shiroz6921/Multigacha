package com.multigacha.contactos.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.multigacha.contactos.model.Contacto;
import com.multigacha.contactos.service.ContactoService;

@WebMvcTest(ContactoController.class)//levanta solo la capa web, no la bd
public class ContactoControllerTest {

    @Autowired
    private MockMvc mock; //mock que simula las peticiones http

    @MockitoBean
    private ContactoService service; //service falso
    private ContactoController controller; //controller falso

    private Contacto contactoEjm;

    @BeforeEach
    void setUp(){

        contactoEjm = new Contacto();

        contactoEjm.setId(1);
        contactoEjm.setTelefono(982666927);
        contactoEjm.setDireccion("Villasana");

    }

    @Test
    void buscarPorId_Retorna200()throws Exception{

        when(controller.buscarContacto(1)).the(contactoEjm);

        mock.perform(get("/api/v1/contactos/1")).andExpect(status().isOk());
    }










}
