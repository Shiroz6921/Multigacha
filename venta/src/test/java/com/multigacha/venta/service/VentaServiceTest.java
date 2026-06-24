package com.multigacha.venta.service;

import com.multigacha.venta.client.ClienteClient;
import com.multigacha.venta.dto.ProductoClienteDTO;
import com.multigacha.venta.model.Venta;
import com.multigacha.venta.repository.VentaRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VentaServiceTest {

    @Mock
    private VentaRepository repo;
    
    @Mock
    private ClienteClient clienteClient;

    @InjectMocks
    private VentaService ventaService;

    private Venta ventaEjem;
    private ProductoClienteDTO productoEnInventario;

    @BeforeEach
    void setUp() {
        ventaEjem = new Venta();
        ventaEjem.setId(1);
        ventaEjem.setIdVendedor(10);
        ventaEjem.setIdProducto(100);
        ventaEjem.setPrecio(15000.0);
        
        productoEnInventario = new ProductoClienteDTO();
        productoEnInventario.setId(100);
        productoEnInventario.setCantidadProd(1);
        productoEnInventario.setNombre("Carta Rara");
    }


    @Test
    void publicarCarta_exitoso() {
        doNothing().when(clienteClient).buscarPorId(10);
        
        List<ProductoClienteDTO> inventarioFalso = new ArrayList<>();
        inventarioFalso.add(productoEnInventario);
        when(clienteClient.obtenerInventarioPorCliente(10)).thenReturn(inventarioFalso);
        
        when(repo.save(any(Venta.class))).thenReturn(ventaEjem);

        Venta resultado = ventaService.publicarCarta(ventaEjem);

        assertNotNull(resultado);
        assertEquals("DISPONIBLE", resultado.getEstado());
        verify(repo, times(1)).save(ventaEjem);
    }

    @Test
    void publicarCarta_VendedorNoExiste_LanzaExcepcion() {
        doThrow(new RuntimeException("Cliente no encontrado en otro Microservicio"))
            .when(clienteClient).buscarPorId(10);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ventaService.publicarCarta(ventaEjem);
        });

        assertEquals("Cliente no encontrado en otro Microservicio", exception.getMessage());
        
        verify(clienteClient, never()).obtenerInventarioPorCliente(anyInt());
        verify(repo, never()).save(any(Venta.class));
    }

    @Test
    void publicarCarta_NoPoseeLaCarta_LanzaExcepcion() {
        ventaEjem.setIdProducto(999); 

        doNothing().when(clienteClient).buscarPorId(10);

        List<ProductoClienteDTO> inventarioFalso = new ArrayList<>();
        inventarioFalso.add(productoEnInventario); 
        when(clienteClient.obtenerInventarioPorCliente(10)).thenReturn(inventarioFalso);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ventaService.publicarCarta(ventaEjem);
        });

        assertEquals("Error: El jugador no posee esta carta en su inventario para venderla.", exception.getMessage());
        verify(repo, never()).save(any(Venta.class));
    }


    @Test
    void obtenerVenta_exitoso() {
        when(repo.findById(1)).thenReturn(Optional.of(ventaEjem));

        Venta resultado = ventaService.obtenerVentaPorId(1);

        assertEquals(1, resultado.getId());
    }

    @Test
    void obtenerVenta_noEncontrado() {
        when(repo.findById(99)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ventaService.obtenerVentaPorId(99);
        });

        assertEquals("Publicación no encontrada", exception.getMessage());
    }


    @Test
    void marcarComoVendida_exitoso() {
        when(repo.findById(1)).thenReturn(Optional.of(ventaEjem));
        when(repo.save(any(Venta.class))).thenReturn(ventaEjem);

        ventaService.marcarComoVendida(1);

        assertEquals("VENDIDA", ventaEjem.getEstado());
        verify(repo, times(1)).save(ventaEjem);
    }

    @Test
    void marcarComoVendida_NoEncontrada() {
        when(repo.findById(99)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ventaService.marcarComoVendida(99);
        });

        assertEquals("Publicación no encontrada", exception.getMessage());
        verify(repo, never()).save(any(Venta.class));
    }
}