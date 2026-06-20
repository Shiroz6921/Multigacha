package com.multigacha.catalogo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.multigacha.catalogo.dto.ProductoDTO;
import com.multigacha.catalogo.model.Categoria;
import com.multigacha.catalogo.model.Producto;
import com.multigacha.catalogo.repository.CategoriaRepository;
import com.multigacha.catalogo.repository.ProductoRepository;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class CatalogoServiceTest {
    @Mock //no es el repo real, solo va a ser una simulacion de repo
    private CategoriaRepository categoriaRepo;
    
    @Mock
    private ProductoRepository productoRepo;

    //el servicio real con el repo simulado inyectado
    @InjectMocks
    private CatalogoService catalogoserv;

    private Categoria categoriaEjem;

    private Producto productoEjem;

    private ProductoDTO prodto;

    @BeforeEach
    void setUp(){

        categoriaEjem = new Categoria();

        productoEjem = new Producto();

        prodto = new ProductoDTO();
        
        categoriaEjem.setId(1);
        categoriaEjem.setColeccion("Espada y escudo");
        categoriaEjem.setFranquicia("Pokemon");

        productoEjem.setId(1);
        productoEjem.setNombre("Pikachu ex");
        productoEjem.setPrecio(15000);
        productoEjem.setStock(10);
        List<Producto> productos = new ArrayList<>();
        productos.add(productoEjem);

        categoriaEjem.setProductos(productos);
        prodto.setNombre("Pikachu ex");
        prodto.setPrecio(15.000);
        prodto.setStock(10);
        prodto.setCategoriaId(1);
    }

    @Test
    void guardar (){
        //Arrange : configuramos que el reposiotry retorne la carta guardado
        when(categoriaRepo.findById(1))
                .thenReturn(Optional.of(categoriaEjem));

        when(productoRepo.save(any(Producto.class)))
                .thenReturn(productoEjem);
        //act
        Producto resultado = catalogoserv.agregarCarta(prodto);

        //assert
        assertEquals("Pikachu ex", resultado.getNombre());
    }

    @Test
    void guardar_CategoriaNoEncontrada(){
        when(categoriaRepo.findById(1))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, ()->{
            catalogoserv.agregarCarta(prodto);
        });

        assertEquals("Categoría no encontrada", exception.getMessage());
    }

    @Test
    void guardar_ErrorAlGuardar(){
        when(categoriaRepo.findById(1))
                .thenReturn(Optional.of(categoriaEjem));

        when(productoRepo.save(any(Producto.class)))
                .thenThrow(new RuntimeException("Error de base de datos"));

        RuntimeException exception = assertThrows(RuntimeException.class, ()->{
            catalogoserv.agregarCarta(prodto);
        });

        assertEquals("Error de base de datos", exception.getMessage());
    }

    @Test
    public void buscarPorId_Encontrado (){

        //arrange: preparamos la prueba, le decimos que hacer

        Optional<Producto> optionalProducto = Optional.of(productoEjem);

        when(productoRepo.findById(1)).thenReturn(optionalProducto);

        Producto resultado = catalogoserv.buscarPorId(1);

        assertEquals(1,resultado.getId());
        assertEquals("Pikachu ex", resultado.getNombre());
    }

    @Test
    void buscarPorId_noEncontrado(){
        Optional<Producto> productoVacio = Optional.empty();
        when(productoRepo.findById(99)).thenReturn(productoVacio);

        RuntimeException exception = assertThrows(RuntimeException.class, ()->{
            catalogoserv.buscarPorId(99);
        });

        assertEquals("Producto no encontrado", exception.getMessage());

    }

    @Test
    void bajarStock_exitoso(){
        when(productoRepo.findById(1))
                .thenReturn(Optional.of(productoEjem));

        when(productoRepo.save(any(Producto.class)))
                .thenReturn(productoEjem);

        Producto resultado = catalogoserv.bajarStock(1, 5);

        assertEquals(5, resultado.getStock());
    }

    @Test
    void bajarStock_stockInsuficiente(){
        when(productoRepo.findById(1))
                .thenReturn(Optional.of(productoEjem));

        RuntimeException exception = assertThrows(RuntimeException.class, ()->{
            catalogoserv.bajarStock(1, 99);
        });

        assertEquals("no hay suficientes cartas", exception.getMessage());
    }

    @Test
    void eliminarProducto_exitoso(){
        when(productoRepo.existsById(1)).thenReturn(true);

        catalogoserv.eliminarProducto(1);
    }

    @Test
    void eliminarProducto_noEncontrado(){
        when(productoRepo.existsById(99)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, ()->{
            catalogoserv.eliminarProducto(99);
        });

        assertEquals("Producto no encontrado", exception.getMessage());
    }

    @Test
    void obtenerTodo(){
        List<Categoria> categorias = new ArrayList<>();
        categorias.add(categoriaEjem);

        when(categoriaRepo.findAll()).thenReturn(categorias);

        List<Categoria> resultado = catalogoserv.obtenerTodo();

        assertEquals(1, resultado.size());
        assertEquals("Espada y escudo", resultado.get(0).getColeccion());
    }

    @Test  
    void obtenerTodo_vacio(){
        when(categoriaRepo.findAll()).thenReturn(new ArrayList<>());

        List<Categoria> resultado = catalogoserv.obtenerTodo();

        assertNotNull(resultado);
        assertEquals(0, resultado.size());
    }

    @Test
    void guardarExpansion(){
        when(categoriaRepo.save(any(Categoria.class)))
                .thenReturn(categoriaEjem);

        Categoria resultado = catalogoserv.guardarExpansion(categoriaEjem);

        assertEquals("Espada y escudo", resultado.getColeccion());
        assertEquals(1, resultado.getProductos().size());
        assertEquals("Pikachu ex", resultado.getProductos().get(0).getNombre());
    }

    @Test
    void guardarExpansion_ErrorAlGuardar(){
        when(categoriaRepo.save(any(Categoria.class)))
                .thenThrow(new RuntimeException("Error de base de datos"));

        RuntimeException exception = assertThrows(RuntimeException.class, ()->{
            catalogoserv.guardarExpansion(categoriaEjem);
        });

        assertEquals("Error de base de datos", exception.getMessage());
    }

    @Test
    void guardarExpansion_SinProductos(){
        Categoria categoriaSinProductos = new Categoria();
        categoriaSinProductos.setId(2);
        categoriaSinProductos.setColeccion("Sol y Luna");
        categoriaSinProductos.setFranquicia("Pokemon");
        categoriaSinProductos.setProductos(null);

        when(categoriaRepo.save(any(Categoria.class)))
                .thenReturn(categoriaSinProductos);

        Categoria resultado = catalogoserv.guardarExpansion(categoriaSinProductos);

        assertEquals("Sol y Luna", resultado.getColeccion());
        assertNull(resultado.getProductos());
    }
}