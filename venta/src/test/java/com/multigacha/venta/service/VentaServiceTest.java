package com.multigacha.venta.service;

import com.multigacha.venta.client.CatalogoClient;
import com.multigacha.venta.client.ClienteClient;
import com.multigacha.venta.client.IntercambioClient;
import com.multigacha.venta.dto.ClienteDTO;
import com.multigacha.venta.dto.ProductoDTO;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VentaServiceTest {

    @Mock
    private VentaRepository repo;
    
    @Mock
    private ClienteClient clienteClient;
    
    @Mock
    private CatalogoClient catalogoClient;
    
    @Mock
    private IntercambioClient intercambioClient;

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
        productoEnInventario.setId(1);
        productoEnInventario.setIdCliente(10);
        productoEnInventario.setIdProducto(100);
    }


    @Test
    void publicarCarta_exitoso() {
        when(clienteClient.buscarPorId(10)).thenReturn(new ClienteDTO()); 
        when(catalogoClient.buscarProductoPorId(100)).thenReturn(new ProductoDTO()); 
        
        List<ProductoClienteDTO> inventarioFalso = new ArrayList<>();
        inventarioFalso.add(productoEnInventario);
        when(intercambioClient.listarInventariosPorCliente(10)).thenReturn(inventarioFalso);
        
        when(repo.save(any(Venta.class))).thenReturn(ventaEjem);

        Venta resultado = ventaService.publicarCarta(ventaEjem);

        assertNotNull(resultado);
        assertEquals("DISPONIBLE", resultado.getEstado());
        verify(repo, times(1)).save(ventaEjem);
    }

    @Test
    void publicarCarta_VendedorNoExiste() {
        when(clienteClient.buscarPorId(10)).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ventaService.publicarCarta(ventaEjem);
        });

        assertEquals("El vendedor no existe.", exception.getMessage());
        verify(repo, never()).save(any(Venta.class));
    }

    @Test
    void publicarCarta_NoPoseeLaCarta() {
        ventaEjem.setIdProducto(999); 

        when(clienteClient.buscarPorId(10)).thenReturn(new ClienteDTO()); 
        when(catalogoClient.buscarProductoPorId(999)).thenReturn(new ProductoDTO()); 

        List<ProductoClienteDTO> inventarioFalso = new ArrayList<>();
        inventarioFalso.add(productoEnInventario); // Este tiene la carta 100
        when(intercambioClient.listarInventariosPorCliente(10)).thenReturn(inventarioFalso);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ventaService.publicarCarta(ventaEjem);
        });

        assertEquals("Error: El jugador no posee esta carta en su inventario para venderla.", exception.getMessage());
        verify(repo, never()).save(any(Venta.class));
    }

    @Test
    void publicarCarta_CartaNoExisteEnCatalogo_LanzaExcepcion() {
        ventaEjem.setIdVendedor(10);
        ventaEjem.setIdProducto(999);

        when(clienteClient.buscarPorId(10)).thenReturn(new ClienteDTO());
        
        when(catalogoClient.buscarProductoPorId(999)).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ventaService.publicarCarta(ventaEjem);
        });

        assertEquals("El producto no existe en el catálogo.", exception.getMessage());
        
        verify(intercambioClient, never()).listarInventariosPorCliente(anyInt());
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