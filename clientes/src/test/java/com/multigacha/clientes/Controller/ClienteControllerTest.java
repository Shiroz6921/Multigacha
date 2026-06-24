package com.multigacha.clientes.Controller;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.multigacha.clientes.controller.ClienteController;
import com.multigacha.clientes.dto.ClienteDTO;
import com.multigacha.clientes.dto.ContactoDTO;
import com.multigacha.clientes.dto.InventarioDTO;
import com.multigacha.clientes.model.Cliente;
import com.multigacha.clientes.model.InventarioCliente;
import com.multigacha.clientes.service.ClienteService;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ClienteController.class)
public class ClienteControllerTest {

    @Autowired
    private MockMvc mock;

    @MockitoBean
    private ClienteService servicio;

    private Cliente clienteEjem;
    private ClienteDTO clienteDTO;
    private InventarioCliente inventarioEjem;
    private InventarioDTO inventarioDTO;

    @BeforeEach
    public void setUp() {
        clienteEjem = new Cliente();
        clienteEjem.setId(1);
        clienteEjem.setRun("12345678-9");
        clienteEjem.setNombre("Juan");
        clienteEjem.setApellido("Pérez");

        inventarioEjem = new InventarioCliente();
        inventarioEjem.setId(1);
        inventarioEjem.setNombre("Pikachu ex");
        inventarioEjem.setCantidadProd(5);

        inventarioDTO = new InventarioDTO();
        inventarioDTO.setNombre("Pikachu ex");
        inventarioDTO.setCantidadProd(5);

        ContactoDTO contactoDTO = new ContactoDTO();
        contactoDTO.setTelefono(123456789);
        contactoDTO.setDireccion("Calle Falsa 123");

        clienteDTO = new ClienteDTO();
        clienteDTO.setRun("12345678-9");
        clienteDTO.setNombre("Juan");
        clienteDTO.setApellido("Pérez");
        clienteDTO.setContacto(contactoDTO);
    }

    @Test
    void listar_Retorna200() throws Exception {
        when(servicio.mostrarClientes()).thenReturn(List.of(clienteEjem));

        mock.perform(get("/api/v1/clientes"))
                .andExpect(status().isOk());
    }

    @Test
    void listar_Retorna204() throws Exception {
        when(servicio.mostrarClientes()).thenReturn(List.of());

        mock.perform(get("/api/v1/clientes"))
                .andExpect(status().isNoContent());
    }

    @Test
    void crear_Retorna200() throws Exception {
        when(servicio.clienteMasInventario(any(ClienteDTO.class))).thenReturn(clienteEjem);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(clienteDTO);

        mock.perform(post("/api/v1/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void crear_Retorna500() throws Exception {
        when(servicio.clienteMasInventario(any(ClienteDTO.class)))
                .thenThrow(new RuntimeException("Error al crear cliente"));

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(clienteDTO);

        mock.perform(post("/api/v1/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void eliminar_Retorna204() throws Exception {
        doNothing().when(servicio).borrarClientePorId(1);

        mock.perform(delete("/api/v1/clientes/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void eliminar_Retorna404() throws Exception {
        doThrow(new RuntimeException("Cliente no encontrado")).when(servicio).borrarClientePorId(99);

        mock.perform(delete("/api/v1/clientes/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void buscarPorId_Retorna200() throws Exception {
        when(servicio.mostrarPorId(1)).thenReturn(clienteEjem);

        mock.perform(get("/api/v1/clientes/1"))
                .andExpect(status().isOk());
    }

    @Test
    void buscarPorId_Retorna404() throws Exception {
        when(servicio.mostrarPorId(99)).thenThrow(new RuntimeException("Cliente no encontrado"));

        mock.perform(get("/api/v1/clientes/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void agregarProductoAlInventario_Retorna200() throws Exception {
        doNothing().when(servicio).agregarProductos(anyInt(), anyList());

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(List.of(inventarioDTO));

        mock.perform(post("/api/v1/clientes/inventario/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void agregarProductoAlInventario_Retorna404() throws Exception {
        doThrow(new RuntimeException("Cliente no registrado")).when(servicio).agregarProductos(anyInt(), anyList());

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(List.of(inventarioDTO));

        mock.perform(post("/api/v1/clientes/inventario/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    void obtenerInventario_Retorna200() throws Exception {
        when(servicio.obtenerInventarioPorCliente(1)).thenReturn(List.of(inventarioEjem));

        mock.perform(get("/api/v1/clientes/inventario/1"))
                .andExpect(status().isOk());
    }

    @Test
    void obtenerInventario_Retorna404() throws Exception {
        when(servicio.obtenerInventarioPorCliente(99)).thenThrow(new RuntimeException("Cliente no encontrado"));

        mock.perform(get("/api/v1/clientes/inventario/99"))
                .andExpect(status().isNotFound());
    }
}
