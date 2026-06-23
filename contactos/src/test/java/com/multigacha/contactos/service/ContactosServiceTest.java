package com.multigacha.contactos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.multigacha.contactos.model.Contacto;
import com.multigacha.contactos.repo.ContactoRepo;

@ExtendWith(MockitoExtension.class)

public class ContactosServiceTest {

    @InjectMocks
    private ContactoService contactoService;

    @Mock
    private ContactoRepo contactoRepository;

    private Contacto contactoEjem;
    
    @BeforeEach
    public void setUp() {
        contactoEjem = new Contacto();
        contactoEjem.setId(1);
        contactoEjem.setTelefono(12345678);
        contactoEjem.setDireccion("Calle Principal 123");
    }

    @Test
    public void GetContacto_encontrado() {
        //arrange: preparamos la prueba, le decimos que hacer

        Optional<Contacto> optionalContacto = Optional.of(contactoEjem);

        when(contactoRepository.findById(1)).thenReturn(optionalContacto);

        Contacto resultado = contactoService.getContacto(1);

        assertEquals(1,resultado.getId());
        assertEquals("Calle Principal 123", resultado.getDireccion());
    }

    @Test
    void getContacto_noEncontrado(){
        Optional<Contacto> contactoVacio = Optional.empty();
        when(contactoRepository.findById(99)).thenReturn(contactoVacio);

        RuntimeException exception = assertThrows(RuntimeException.class, ()->{
            contactoService.getContacto(99);
        });

        assertEquals("Contacto no encontrado", exception.getMessage());
    }

    @Test
    void listarContactos_exitoso(){
        List<Contacto> contactos = new ArrayList<>();
        contactos.add(contactoEjem);

        when(contactoRepository.findAll()).thenReturn(contactos);

        List<Contacto> resultado = contactoService.listarContactos();

        assertEquals(1, resultado.size());
        assertEquals(contactoEjem.getId(), resultado.get(0).getId());
    }

    @Test
    void listarContactos_vacio(){
        when(contactoRepository.findAll()).thenReturn(new ArrayList<>());

        List<Contacto> resultado = contactoService.listarContactos();

        assertEquals(0, resultado.size());
    }

    @Test
    void addContacto_exitoso(){
        when(contactoRepository.save(any(Contacto.class)))
                .thenReturn(contactoEjem);
        //act
        Contacto resultado = contactoService.addContacto(contactoEjem);

        //assert
        assertEquals("Calle Principal 123", resultado.getDireccion());
    }

    @Test
    void addContacto_errorAlGuardar(){
        when(contactoRepository.save(any(Contacto.class)))
                .thenThrow(new RuntimeException("Error al guardar el contacto"));
        //act & assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            contactoService.addContacto(contactoEjem);
        });

        assertEquals("Error al guardar el contacto", exception.getMessage());
    }

    @Test
    void deleteContacto_exitoso(){
        when(contactoRepository.existsById(1)).thenReturn(true);
        doNothing().when(contactoRepository).deleteById(1);

        contactoService.deleteContacto(1);

        verify(contactoRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteContacto_noEncontrado(){
        when(contactoRepository.existsById(99)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, ()->{
            contactoService.deleteContacto(99);
        });

        assertEquals("Contacto no encontrado", exception.getMessage());
    }

    @Test
    void actualizarContacto_exitoso(){
        when(contactoRepository.findById(1)).thenReturn(Optional.of(contactoEjem));
        when(contactoRepository.save(any(Contacto.class))).thenReturn(contactoEjem);

        Contacto contactoNuevo = new Contacto();
        contactoNuevo.setId(1);
        contactoNuevo.setTelefono(87654321);
        contactoNuevo.setDireccion("Calle Secundaria 456");

        Contacto resultado = contactoService.actualizarContacto(contactoNuevo);

        assertEquals("Calle Secundaria 456", resultado.getDireccion());
    }

    @Test
    void actualizarContacto_noEncontrado(){
        when(contactoRepository.findById(99)).thenReturn(Optional.empty());

        Contacto contactoNuevo = new Contacto();
        contactoNuevo.setId(99);
        contactoNuevo.setTelefono(87654321);
        contactoNuevo.setDireccion("Calle Secundaria 456");

        RuntimeException exception = assertThrows(RuntimeException.class, ()->{
            contactoService.actualizarContacto(contactoNuevo);
        });

        assertEquals("Contacto no encontrado", exception.getMessage());
    }
} 
