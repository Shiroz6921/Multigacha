package com.multigacha.compra.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
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

import com.multigacha.compra.client.BoletaClient;
import com.multigacha.compra.client.CarritoClient;
import com.multigacha.compra.client.CatalogoClient;
import com.multigacha.compra.client.ClientesClient;
import com.multigacha.compra.dto.BoletaDTO;
import com.multigacha.compra.dto.CarritoDTO;
import com.multigacha.compra.dto.CompraBoletaDTO;
import com.multigacha.compra.dto.ProductoCarritoDTO;
import com.multigacha.compra.model.Compra;
import com.multigacha.compra.repository.CompraRepository;

@ExtendWith(MockitoExtension.class)
public class CompraServiceTest {

    @InjectMocks
    private CompraService compraService;

    @Mock
    private CompraRepository repo;

    @Mock
    private CarritoClient carritoClient;

    @Mock
    private CatalogoClient catalogoClient;

    @Mock
    private BoletaClient boletaClient;

    @Mock
    private ClientesClient clientesClient;

    private Compra compraEjem;
    private CarritoDTO carritoEjem;
    private ProductoCarritoDTO productoEjem;
    private BoletaDTO boletaEjem;

    @BeforeEach
    public void setUp() {
        productoEjem = new ProductoCarritoDTO();
        productoEjem.setId(1);
        productoEjem.setProductoId(1);
        productoEjem.setNombre("Pikachu ex");
        productoEjem.setPrecio(1000.00);
        productoEjem.setCantidad(2);

        carritoEjem = new CarritoDTO();
        carritoEjem.setId(1);
        carritoEjem.setTotal(2000.00);
        carritoEjem.setItems(List.of(productoEjem));

        boletaEjem = new BoletaDTO();
        boletaEjem.setIdComprador(1);
        boletaEjem.setIdVendedor(0);
        boletaEjem.setMontoTotal(2000.00);
        boletaEjem.setDetalle("Compra desde Carrito ID: 1 con 1 productos distintos.");

        compraEjem = new Compra();
        compraEjem.setId(1);
        compraEjem.setIdComprador(1);
        compraEjem.setIdVenta(1);
        compraEjem.setTotalPagado(2000.00);
        compraEjem.setFechaCompra(new Date());
    }

    @Test
    public void procesarCompra_exitoso() {
        when(carritoClient.buscarPorId(1)).thenReturn(carritoEjem);
        doNothing().when(catalogoClient).reducirStock(anyInt(), anyInt());
        doNothing().when(clientesClient).agregarProductosAlInventario(anyInt(), anyList());
        when(boletaClient.generarBoleta(any(BoletaDTO.class))).thenReturn(boletaEjem);
        when(repo.save(any(Compra.class))).thenReturn(compraEjem);

        CompraBoletaDTO resultado = compraService.procesarCompraDesdeCarrito(1, 1);

        assertNotNull(resultado);
        assertEquals(compraEjem, resultado.getCompra());
        verify(repo, times(1)).save(any(Compra.class));
    }

    @Test
    public void procesarCompra_carritoVacio() {
        carritoEjem.setItems(List.of());
        when(carritoClient.buscarPorId(1)).thenReturn(carritoEjem);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            compraService.procesarCompraDesdeCarrito(1, 1);
        });

        assertEquals("El carrito está vacío.", exception.getMessage());
    }

    @Test
    public void procesarCompra_errorAlGuardar() {
        when(carritoClient.buscarPorId(1)).thenReturn(carritoEjem);
        doNothing().when(catalogoClient).reducirStock(anyInt(), anyInt());
        doNothing().when(clientesClient).agregarProductosAlInventario(anyInt(), anyList());
        when(boletaClient.generarBoleta(any(BoletaDTO.class))).thenReturn(boletaEjem);
        when(repo.save(any(Compra.class))).thenThrow(new RuntimeException("Error al guardar compra"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            compraService.procesarCompraDesdeCarrito(1, 1);
        });

        assertEquals("Error al guardar compra", exception.getMessage());
    }

    @Test
    public void listar_exitoso() {
        when(repo.findAll()).thenReturn(List.of(compraEjem));

        List<Compra> resultado = compraService.listar();

        assertEquals(1, resultado.size());
        assertEquals(compraEjem.getId(), resultado.get(0).getId());
    }

    @Test
    public void listar_vacio() {
        when(repo.findAll()).thenReturn(List.of());

        List<Compra> resultado = compraService.listar();

        assertTrue(resultado.isEmpty());
    }

    @Test
    public void buscarPorId_exitoso() {
        when(repo.findById(1)).thenReturn(Optional.of(compraEjem));

        Compra resultado = compraService.buscarPorId(1);

        assertEquals(compraEjem.getId(), resultado.getId());
    }

    @Test
    public void buscarPorId_noEncontrado() {
        when(repo.findById(99)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            compraService.buscarPorId(99);
        });

        assertEquals("Compra no encontrado", exception.getMessage());
    }
}
