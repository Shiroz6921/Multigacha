package com.multigacha.contactos.service;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.multigacha.contactos.model.Contacto;
import com.multigacha.contactos.repo.ContactoRepo;



@ExtendWith(MockitoExtension.class)
public class ContactoServiceTest {

    @Mock
    ContactoRepo contactoRepository;


    @InjectMocks
    private ContactoService contactoServ;

    private Contacto contactoEjm;



    @BeforeEach
    void setUp(){

        contactoEjm = new Contacto();

        contactoEjm.setId(1);
        contactoEjm.setDireccion("villasana");
        contactoEjm.setTelefono(982666927);
        
    }

    @Test
    void guardar(){
 
    }


    




























}