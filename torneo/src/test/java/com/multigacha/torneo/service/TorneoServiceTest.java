package com.multigacha.torneo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
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

import com.multigacha.torneo.client.ClientesClient;
import com.multigacha.torneo.dto.ClienteDTO;
import com.multigacha.torneo.model.inscripciones;
import com.multigacha.torneo.model.torneo;
import com.multigacha.torneo.repository.InscripcionesRepository;
import com.multigacha.torneo.repository.TorneoRepository;

@ExtendWith(MockitoExtension.class)
public class TorneoServiceTest {

    @Mock
        private InscripcionesRepository repo1; 
    @Mock
        private TorneoRepository repo2; 
    @Mock
        private ClientesClient clienteClient;

    @InjectMocks
        private TorneoService torneoService;
        private torneo torneoEjem;
        private inscripciones inscripcionEjem;
        private ClienteDTO clienteDTO;
    
    @BeforeEach
    void setUp() {
        torneoEjem = new torneo();
        torneoEjem.setId(1);
        torneoEjem.setNombre("Pokemon Championship");
        torneoEjem.setMaxParticipantes(16);

        clienteDTO = new ClienteDTO();
        clienteDTO.setId(100);
        clienteDTO.setNombre("Ash Ketchum");

        inscripcionEjem = new inscripciones();
        inscripcionEjem.setId(10);
        inscripcionEjem.setClienteId(100);
        inscripcionEjem.setNombre("Ash Ketchum");
        inscripcionEjem.setTorneo(torneoEjem);
        }

    @Test
    void crearTorneo_exitoso() {
        when(repo2.save(any(torneo.class))).thenReturn(torneoEjem);

        torneo resultado = torneoService.crearTorneo(torneoEjem);

        assertNotNull(resultado);
        assertEquals("Pokemon Championship", resultado.getNombre());
        assertEquals(16, resultado.getMaxParticipantes());
    }

    @Test
    void crearTorneo_errorBaseDatos() {
        when(repo2.save(any(torneo.class)))
            .thenThrow(new RuntimeException("Error al guardar en la base de datos"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            torneoService.crearTorneo(torneoEjem);
        });

        assertEquals("Error al guardar en la base de datos", exception.getMessage());
    }

    @Test
    void agregarGente_exitoso() {
        when(clienteClient.buscarPorId(100)).thenReturn(clienteDTO);
        when(repo2.findById(1)).thenReturn(Optional.of(torneoEjem));
        when(repo1.existsByClienteIdAndTorneoId(100, 1)).thenReturn(false);
        when(repo1.save(any(inscripciones.class))).thenReturn(inscripcionEjem);

        inscripciones resultado = torneoService.agregarGente(1, clienteDTO);

        assertNotNull(resultado);
        assertEquals("Ash Ketchum", resultado.getNombre());
        assertEquals(1, resultado.getTorneo().getId());
    }

    @Test
    void agregarGente_torneoNoEncontrado() {
        when(clienteClient.buscarPorId(100)).thenReturn(clienteDTO);
        when(repo2.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            torneoService.agregarGente(1, clienteDTO);
        });

        assertEquals("Torneo no encontrado", exception.getMessage());
    }

    @Test
    void agregarGente_personaNoExiste() {
        when(clienteClient.buscarPorId(100))
            .thenThrow(new RuntimeException("Cliente no encontrado"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            torneoService.agregarGente(1, clienteDTO);
        });

        assertEquals("Cliente no encontrado", exception.getMessage());
        
        verify(repo1, never()).save(any(inscripciones.class));
    }

    @Test
    void agregarGente_yaInscrito() {
        when(clienteClient.buscarPorId(100)).thenReturn(clienteDTO);
        when(repo2.findById(1)).thenReturn(Optional.of(torneoEjem));
        when(repo1.existsByClienteIdAndTorneoId(100, 1)).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            torneoService.agregarGente(1, clienteDTO);
        });

        assertEquals("Esta persona ya se encuentra inscrita en este torneo.", exception.getMessage());
    }

    @Test
    void obtenerTodo_exitoso() {
        List<inscripciones> listaFalsa = new ArrayList<>();
        listaFalsa.add(inscripcionEjem);
        when(repo1.findAll()).thenReturn(listaFalsa);

        List<inscripciones> resultado = torneoService.obtenerTodo();

        assertEquals(1, resultado.size());
        assertEquals("Ash Ketchum", resultado.get(0).getNombre());
    }

    @Test
    void obtenerTodo_vacio() {
        when(repo1.findAll()).thenReturn(new ArrayList<>());

        List<inscripciones> resultado = torneoService.obtenerTodo();

        assertNotNull(resultado);
        assertEquals(0, resultado.size());
    }


    @Test
    void borrarTorneo_exitoso() {
        when(repo2.existsById(1)).thenReturn(true);

        torneoService.borrarTorneo(1);

        verify(repo2, times(1)).deleteById(1);
    }

    @Test
    void borrarTorneo_noEncontrado() {
        when(repo2.existsById(99)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            torneoService.borrarTorneo(99);
        });

        assertEquals("Torneo no encontrado", exception.getMessage());
        verify(repo2, never()).deleteById(anyInt());
    }







}