package com.multigacha.intercambio.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.multigacha.intercambio.dto.IntercambioRequestDTO;
import com.multigacha.intercambio.model.Intercambio;
import com.multigacha.intercambio.model.ProductoCliente;
import com.multigacha.intercambio.repo.IntercambioRepo;
import com.multigacha.intercambio.repo.ProductoClienteRepo;

@ExtendWith(MockitoExtension.class)
public class IntercambioServiceTest {

    @InjectMocks
    private IntercambioService intercambioService;

    @Mock
    private IntercambioRepo repo1;

    @Mock
    private ProductoClienteRepo repo2;

    private Intercambio intercambioEjem;
    private ProductoCliente productoClienteA;
    private ProductoCliente productoClienteB;
    private IntercambioRequestDTO requestDTO;

    @BeforeEach
    public void setUp() {
        productoClienteA = new ProductoCliente();
        productoClienteA.setId(1);
        productoClienteA.setIdCliente(1);
        productoClienteA.setIdProducto(1);

        productoClienteB = new ProductoCliente();
        productoClienteB.setId(2);
        productoClienteB.setIdCliente(2);
        productoClienteB.setIdProducto(2);

        intercambioEjem = new Intercambio();
        intercambioEjem.setId(1);
        intercambioEjem.setFecha(new Date());
        intercambioEjem.setTraspaso(productoClienteA);

        requestDTO = new IntercambioRequestDTO();
        requestDTO.setProductoA(1);
        requestDTO.setProductoB(2);
    }

    @Test
    public void intercambio_exitoso() {
        when(repo2.save(any(ProductoCliente.class))).thenReturn(productoClienteA);

        ProductoCliente resultado = intercambioService.intercambio(productoClienteA);

        assertEquals(productoClienteA.getId(), resultado.getId());
        verify(repo2, times(1)).save(any(ProductoCliente.class));
    }

    @Test
    public void crearIntercambio_exitoso() {
        when(repo2.findByIdProducto(1)).thenReturn(productoClienteA);
        when(repo2.findByIdProducto(2)).thenReturn(productoClienteB);
        when(repo2.save(any(ProductoCliente.class))).thenReturn(productoClienteA);
        when(repo1.save(any(Intercambio.class))).thenAnswer(invocation -> {
            Intercambio i = invocation.getArgument(0);
            i.setId(1); 
            return i;
        });

        Intercambio resultado = intercambioService.crearIntercambio(requestDTO);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        verify(repo2, times(2)).save(any(ProductoCliente.class));
        verify(repo1, times(1)).save(any(Intercambio.class));
    }
    @Test
    public void crearIntercambio_productosNoEncontrados() {
        when(repo2.findByIdProducto(1)).thenReturn(null);
        when(repo2.findByIdProducto(2)).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            intercambioService.crearIntercambio(requestDTO);
        });

        assertEquals("Uno o ambos productos no se encuentran", exception.getMessage());
    }

    @Test
    public void crearIntercambio_productoANull() {
        when(repo2.findByIdProducto(1)).thenReturn(null);
        when(repo2.findByIdProducto(2)).thenReturn(productoClienteB);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            intercambioService.crearIntercambio(requestDTO);
        });

        assertEquals("Uno o ambos productos no se encuentran", exception.getMessage());
    }

    @Test
    public void eliminarIntercambio_exitoso() {
        doNothing().when(repo1).deleteById(1);

        intercambioService.eliminarIntercambio(1);

        verify(repo1, times(1)).deleteById(1);
    }

    @Test
    public void eliminarIntercambio_noEncontrado() {
        doThrow(new RuntimeException("Intercambio no encontrado")).when(repo1).deleteById(99);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            intercambioService.eliminarIntercambio(99);
        });

        assertEquals("Intercambio no encontrado", exception.getMessage());
    }

    @Test
    public void obtenerTodos_exitoso() {
        when(repo1.findAll()).thenReturn(List.of(intercambioEjem));

        List<Intercambio> resultado = intercambioService.obtenerTodos();

        assertEquals(1, resultado.size());
        assertEquals(intercambioEjem.getId(), resultado.get(0).getId());
    }

    @Test
    public void obtenerTodos_vacio() {
        when(repo1.findAll()).thenReturn(List.of());

        List<Intercambio> resultado = intercambioService.obtenerTodos();

        assertTrue(resultado.isEmpty());
    }

    @Test
    public void buscarPorId_exitoso() {
        when(repo1.findById(1)).thenReturn(Optional.of(intercambioEjem));

        Intercambio resultado = intercambioService.buscarPorId(1);

        assertEquals(intercambioEjem.getId(), resultado.getId());
    }

    @Test
    public void buscarPorId_noEncontrado() {
        when(repo1.findById(99)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            intercambioService.buscarPorId(99);
        });

        assertEquals("Intercambio no encontrado", exception.getMessage());
    }
}