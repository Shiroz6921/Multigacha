package com.multigacha.catalogo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.multigacha.catalogo.dto.ProductoDTO;
import com.multigacha.catalogo.model.Categoria;
import com.multigacha.catalogo.model.Producto;
import com.multigacha.catalogo.service.CatalogoService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CatalogoController.class)//levanta solo la capa web, no la bd
public class CatalogoControllerTest {

    @Autowired
    private MockMvc mock; //mock que simula las peticiones http

    @MockitoBean
    private CatalogoService service; //service falso

    private Producto productoEjem;
    private Categoria categoriaEjem;
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
        productoEjem.setCategoria(categoriaEjem);
        List<Producto> productos = new ArrayList<>();
        productos.add(productoEjem);

        categoriaEjem.setProductos(productos);
        prodto.setNombre("Pikachu ex");
        prodto.setPrecio(15.000);
        prodto.setStock(10);
        prodto.setCategoriaId(1);
    }

    @Test
    void buscarPorId_Retorna200()throws Exception{
        //Arrange : el service dabe retornar el doctor
        when(service.buscarPorId(1)).thenReturn(productoEjem);

        //act + assert 
        mock.perform(get("/api/v1/catalogo/1")).andExpect(status().isOk());
    }

    @Test
    void buscarPorId_Retorna400()throws Exception{
        //arrange: buscamos un producto con id 99 y tira un error 
        when(service.buscarPorId(99)).thenThrow(new RuntimeException("Producto no encontrado"));

        //act + assert 
        mock.perform(get("/api/v1/catalogo/99"))
            .andExpect(status().isNotFound()); // O SEA UN CODIGO HTTPS 404
    }
    
    @Test  
    void listarTodo_Retorna200()throws Exception{
        List<Categoria> categorias = new ArrayList<>();
        categorias.add(categoriaEjem);

        when(service.obtenerTodo()).thenReturn(categorias);

        mock.perform(get("/api/v1/catalogo/todo"))
            .andExpect(status().isOk());
    }

    @Test  
    void listarTodo_Retorna204()throws Exception{
        when(service.obtenerTodo()).thenReturn(new ArrayList<>());

        mock.perform(get("/api/v1/catalogo/todo"))
            .andExpect(status().isNoContent());
    }
    @Test
    void expansion_Retorna200() throws Exception {
    when(service.guardarExpansion(categoriaEjem)).thenReturn(categoriaEjem);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(categoriaEjem);

        mock.perform(post("/api/v1/catalogo/expansion")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(status().isOk());
    }

    @Test
    void expansion_Retorna404() throws Exception {
    when(service.guardarExpansion(any(Categoria.class)))
            .thenThrow(new RuntimeException("Error al guardar la expansion"));
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(categoriaEjem);

        mock.perform(post("/api/v1/catalogo/expansion")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(status().isBadRequest());
    }
    
    @Test
    void agregarCarta_Retorna200() throws Exception {
        when(service.agregarCarta(prodto)).thenReturn(productoEjem);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(prodto);

        mock.perform(post("/api/v1/catalogo/carta")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(status().isOk());
    }

    @Test
    void agregarCarta_Retorna404() throws Exception {
        when(service.agregarCarta(any(ProductoDTO.class)))
            .thenThrow(new RuntimeException("Error al agregar la carta"));

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(prodto);

        mock.perform(post("/api/v1/catalogo/carta")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(status().isNotFound());
    }

    @Test  
    void eliminarProducto_Retorna200() throws Exception {
        doNothing().when(service).eliminarProducto(1);

        mock.perform(delete("/api/v1/catalogo/1/eliminar"))
            .andExpect(status().isOk());
    }
    
    @Test  
    void eliminarProducto_Retorna404() throws Exception {
        doThrow(new RuntimeException("Producto no encontrado")).when(service).eliminarProducto(99);

        mock.perform(delete("/api/v1/catalogo/99/eliminar"))
            .andExpect(status().isNotFound());
    } 

    @Test
    void reducirStock_Retorna200()throws Exception{
        when(service.bajarStock(1, 5)).thenReturn(productoEjem);

         mock.perform(put("/api/v1/catalogo/1/reducir-stock/5"))
            .andExpect(status().isOk());
        }

    @Test
    void reducirStock_Retorna404()throws Exception{
        when(service.bajarStock(99, 99)).thenThrow(new RuntimeException("Producto no encontrado"));
        mock.perform(put("/api/v1/catalogo/99/reducir-stock/99"))
            .andExpect(status().isNotFound()); 
    }
}