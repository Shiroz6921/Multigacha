package com.multigacha.carrito.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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

import com.multigacha.carrito.client.CatalogoClient;
import com.multigacha.carrito.client.ClientesClient;
import com.multigacha.carrito.dto.CarritoDTO;
import com.multigacha.carrito.dto.ClienteDTO;
import com.multigacha.carrito.dto.ProductoDTO;
import com.multigacha.carrito.model.Carrito;
import com.multigacha.carrito.model.ProductosCarritos;
import com.multigacha.carrito.repository.CarritoRepository;

@ExtendWith(MockitoExtension.class)
public class CarritoServiceTest {

    @InjectMocks
    private CarritoService carritoService;

    @Mock
    private CarritoRepository carritoRepository;

    @Mock
    private CatalogoClient catalogoClient;

    @Mock
    private ClientesClient cliente;

    private Carrito carritoEjem;
    private ProductosCarritos prodCarrito;
    private CarritoDTO carritoDTO;
    private ClienteDTO clienteDTO;
    private ProductoDTO prodDto;

    @BeforeEach
    public void setUp(){
        carritoEjem = new Carrito();
        prodCarrito = new ProductosCarritos();
        carritoDTO = new CarritoDTO();
        clienteDTO = new ClienteDTO();
        prodDto = new ProductoDTO();

        carritoEjem.setId(1);
        carritoEjem.setClienteId(1);
        carritoEjem.setTotal(1800.0);

        prodCarrito.setId(1);
        prodCarrito.setNombre("Pikachu ex");
        prodCarrito.setPrecio(1000.00);
        prodCarrito.setProductoId(1);
        prodCarrito.setCantidad(10);
        prodCarrito.setCarrito(carritoEjem);
        List<ProductosCarritos> productos = new ArrayList<>();
        productos.add(prodCarrito);
        carritoEjem.setItems(productos);

        carritoDTO.setCantidad(10);
        carritoDTO.setProductoId(1);

        clienteDTO.setId(1);

        prodDto.setId(1);
        prodDto.setNombre("Pikachu ex");
        prodDto.setPrecio(1000.00);
    }

    @Test
    public void crearCarrito_exitoso() {
        when(cliente.buscarPorId(1)).thenReturn(clienteDTO);
        when(carritoRepository.save(any(Carrito.class))).thenReturn(carritoEjem);

        Carrito resultado = carritoService.crearCarrito(1);

        assertEquals(carritoEjem.getId(), resultado.getId());
        assertEquals(carritoEjem.getClienteId(), resultado.getClienteId());
    }

    @Test
    public void crearCarrito_clienteNoEncontrado() {
        when(cliente.buscarPorId(99)).thenThrow(new RuntimeException("Cliente no encontrado"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            carritoService.crearCarrito(99);
        });

        assertEquals("Cliente no encontrado", exception.getMessage());
    }

    @Test
    public void agregarProducto_exitoso() {
        when(catalogoClient.buscarProductoPorId(1)).thenReturn(prodDto);
        when(carritoRepository.findById(1)).thenReturn(Optional.of(carritoEjem));
        when(carritoRepository.save(any(Carrito.class))).thenReturn(carritoEjem);

        Carrito resultado = carritoService.agregarProducto(1, carritoDTO);

        assertEquals(carritoEjem.getId(), resultado.getId());
        verify(carritoRepository, times(1)).save(any(Carrito.class));
    }

    @Test
    public void agregarProducto_carritoNoEncontrado() {
        when(catalogoClient.buscarProductoPorId(1)).thenReturn(prodDto);
        when(carritoRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            carritoService.agregarProducto(99, carritoDTO);
        });

        assertEquals("Carrito no encontrado", exception.getMessage());
    }

    @Test
    public void listarTodos_exitoso() {
        when(carritoRepository.findAll()).thenReturn(List.of(carritoEjem));

        List<Carrito> resultado = carritoService.listarTodos();

        assertEquals(1, resultado.size());
        assertEquals(carritoEjem.getId(), resultado.get(0).getId());
    }

    @Test
    public void buscarId_exitoso() {
        when(carritoRepository.findById(1)).thenReturn(Optional.of(carritoEjem));

        Carrito resultado = carritoService.buscarId(1);

        assertEquals(carritoEjem.getId(), resultado.getId());
    }

    @Test
    public void buscarId_noEncontrado() {
        when(carritoRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            carritoService.buscarId(99);
        });

        assertEquals("Carrito no encontrado", exception.getMessage());
    }
}