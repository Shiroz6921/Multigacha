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

    // 1. LOS ACTORES FALSOS (Mocks)
    @Mock
    private ContactoRepo contactoRepo;

    // 2. EL DIRECTOR (El servicio real)
    @InjectMocks
    private ContactoService contactoService;

    // Variables globales para la prueba
    private Contacto contactoEjem;
    private ContactoDTO contactoDTO;

    // 3. PREPARANDO EL ESCENARIO
    @BeforeEach
    void setUp() {
        contactoEjem = new Contacto();
        contactoDTO = new ContactoDTO();

        // Llenamos la Entidad simulada usando TUS campos exactos
        contactoEjem.setId(1);
        contactoEjem.setDireccion("Av. Providencia 1234, Santiago");
        contactoEjem.setTelefono(912345678);

        // Llenamos el DTO de la misma manera
        contactoDTO.setDireccion("Av. Providencia 1234, Santiago");
        contactoEjem.setTelefono(912345678);
    }

    // --- EMPIEZAN LAS PRUEBAS ---

    @Test
    void guardarContacto_exitoso() {
        // Arrange: Al guardar, el repo retorna nuestro contactoEjemplo
        when(contactoRepo.save(any(Contacto.class))).thenReturn(contactoEjem);

        // Act: Ejecutamos el método del servicio
        Contacto resultado = contactoService.addContacto(contactoEjem);

        // Assert: Verificamos que los datos coincidan con TUS campos
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Av. Providencia 1234, Santiago", resultado.getDireccion());
        assertEquals(912345678, resultado.getTelefono());
    }

    @Test
    void buscarPorId_encontrado() {
        // Arrange: Simulamos que encuentra el ID 1
        when(contactoRepo.findById(1)).thenReturn(Optional.of(contactoEjem));

        // Act
        Contacto resultado = contactoService.getContacto(1);

        // Assert
        assertEquals(1, resultado.getId());
        assertEquals(912345678, resultado.getTelefono());
    }

    @Test
    void buscarPorId_noEncontrado() {
        // Arrange: Simulamos que devuelve vacío
        when(contactoRepo.findById(99)).thenReturn(Optional.empty());

        // Act & Assert (Esperamos que explote con tu mensaje de error)
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            contactoService.getContacto(99);
        });

        // NOTA: Revisa que este texto sea el mismo que pusiste en tu ContactoService real
        assertEquals("Contacto no encontrado", exception.getMessage());
    }

    @Test
    void obtenerTodosLosContactos_exitoso() {
        // Arrange
        List<Contacto> listaFalsa = new ArrayList<>();
        listaFalsa.add(contactoEjem);
        when(contactoRepo.findAll()).thenReturn(listaFalsa);

        // Act
        List<Contacto> resultado = contactoService.listarContactos();

        // Assert
        assertEquals(1, resultado.size());
        assertEquals("Av. Providencia 1234, Santiago", resultado.get(0).getDireccion());
    }

    @Test
    void eliminarContacto_exitoso() {
        // Arrange
        when(contactoRepo.existsById(1)).thenReturn(true);

        // Act
        contactoService.deleteContacto(1);

        // Assert: Verificamos que intentó borrarlo 1 sola vez
        verify(contactoRepo, times(1)).deleteById(1);
    }
    
    @Test
    void eliminarContacto_noEncontrado() {
        // Arrange
        when(contactoRepo.existsById(99)).thenReturn(false);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            contactoService.deleteContacto(99);
        });

        assertEquals("Contacto no encontrado", exception.getMessage());
        
        // Assert: Verificamos que NUNCA llamó a la base de datos para borrar
        verify(contactoRepo, never()).deleteById(anyInt());
    }
}