package com.multigacha.contactos.service;

import com.multigacha.contactos.dto.ContactoDTO;
import com.multigacha.contactos.model.Contacto;
import com.multigacha.contactos.repo.ContactoRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContactoServiceTest {

    @Mock
    private ContactoRepo contactoRepo;

    @InjectMocks
    private ContactoService contactoService;

    private Contacto contactoEjem;
    private ContactoDTO dto;

    @BeforeEach
    void setUp() {
        contactoEjem = new Contacto();
        contactoEjem.setId(1);
        contactoEjem.setTelefono(123456789);
        contactoEjem.setDireccion("Calle Falsa 123");

        dto = new ContactoDTO();
        dto.setId(1);
        dto.setDireccion("calle falsa 2");
        dto.setTelefono(1234566);
    }

    @Test
    void listarContactos_exitoso() {
        List<Contacto> listaFalsa = new ArrayList<>();
        listaFalsa.add(contactoEjem);
        when(contactoRepo.findAll()).thenReturn(listaFalsa);

        List<Contacto> resultado = contactoService.listarContactos();

        assertEquals(1, resultado.size());
        assertEquals("Calle Falsa 123", resultado.get(0).getDireccion());
    }

    @Test
    void listarContactos_vacio() {
        when(contactoRepo.findAll()).thenReturn(new ArrayList<>());

        List<Contacto> resultado = contactoService.listarContactos();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }


    @Test
    void getContacto_encontrado() {
        when(contactoRepo.findById(1)).thenReturn(Optional.of(contactoEjem));

        Contacto resultado = contactoService.getContacto(1);

        assertEquals(1, resultado.getId());
        assertEquals(123456789, resultado.getTelefono());
    }

    @Test
    void getContacto_noEncontrado() {
        when(contactoRepo.findById(99)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            contactoService.getContacto(99);
        });

        assertEquals("Contacto no encontrado", exception.getMessage());
    }


    @Test
    void addContacto_exitoso() {
        when(contactoRepo.save(any(Contacto.class))).thenReturn(contactoEjem);

        // ← usa DTO en vez de contactoEjem
        Contacto resultado = contactoService.addContacto(dto);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Calle Falsa 123", resultado.getDireccion());
        assertEquals(123456789, resultado.getTelefono());
    }

    @Test
    void addContacto_errorBaseDatos() {
        when(contactoRepo.save(any(Contacto.class)))
                .thenThrow(new RuntimeException("Error al agregar contacto"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            contactoService.addContacto(dto);
        });

        assertEquals("Error al agregar contacto", exception.getMessage());
    }


    @Test
    void actualizarContacto_exitoso() {
        when(contactoRepo.findById(1)).thenReturn(Optional.of(contactoEjem));
        when(contactoRepo.save(any(Contacto.class))).thenReturn(contactoEjem);

        Contacto resultado = contactoService.actualizarContacto(contactoEjem);

        assertNotNull(resultado);
        assertEquals("Calle Falsa 123", resultado.getDireccion());
    }

    @Test
    void actualizarContacto_noEncontrado() {
        when(contactoRepo.findById(99)).thenReturn(Optional.empty());

        Contacto contactoActualizar = new Contacto();
        contactoActualizar.setId(99);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            contactoService.actualizarContacto(contactoActualizar);
        });

        assertEquals("Contacto no encontrado", exception.getMessage());
    }


    @Test
    void deleteContacto_exitoso() {
        when(contactoRepo.existsById(1)).thenReturn(true);

        contactoService.deleteContacto(1);

    }
    
    @Test
    void deleteContacto_noEncontrado() {
        when(contactoRepo.existsById(99)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            contactoService.deleteContacto(99);
        });

        assertEquals("Contacto no encontrado", exception.getMessage());
        verify(contactoRepo, never()).deleteById(anyInt());
    }
}