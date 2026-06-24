package com.multigacha.clientes.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.multigacha.clientes.client.ContactoClient;
import com.multigacha.clientes.dto.ClienteDTO;
import com.multigacha.clientes.dto.ContactoDTO;
import com.multigacha.clientes.dto.InventarioDTO;
import com.multigacha.clientes.model.Cliente;
import com.multigacha.clientes.model.InventarioCliente;
import com.multigacha.clientes.repo.ClienteRepo;
import com.multigacha.clientes.repo.InventarioRepository;
import com.multigacha.clientes.service.ClienteService;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ClienteRepo repo;

    @Mock
    private ContactoClient contacto;

    @Mock
    private InventarioRepository repoInv;

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
        inventarioEjem.setCliente(clienteEjem);

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
    public void mostrarClientes_exitoso() {
        when(repo.findAll()).thenReturn(List.of(clienteEjem));

        List<Cliente> resultado = clienteService.mostrarClientes();

        assertEquals(1, resultado.size());
        assertEquals(clienteEjem.getId(), resultado.get(0).getId());
    }
    
    @Test
    public void mostrarClientes_noExitoso() {
        when(repo.findAll()).thenReturn(List.of());

        List<Cliente> resultado = clienteService.mostrarClientes();

        assertTrue(resultado.isEmpty());
    }

    @Test
    public void mostrarPorId_exitoso() {
        when(repo.findById(1)).thenReturn(Optional.of(clienteEjem));

        Cliente resultado = clienteService.mostrarPorId(1);

        assertEquals(clienteEjem.getId(), resultado.getId());
        assertEquals(clienteEjem.getNombre(), resultado.getNombre());
    }

    @Test
    public void mostrarPorId_noEncontrado() {
        when(repo.findById(99)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            clienteService.mostrarPorId(99);
        });

        assertEquals("Cliente no encontrado", exception.getMessage());
    }

    @Test
    public void clienteMasInventario_exitoso() {
        when(repo.save(any(Cliente.class))).thenReturn(clienteEjem);

        Cliente resultado = clienteService.clienteMasInventario(clienteDTO);

        assertEquals(clienteEjem.getNombre(), resultado.getNombre());
        verify(contacto, times(1)).agregarContacto(any(ContactoDTO.class));
        verify(repo, times(1)).save(any(Cliente.class));
    }

    @Test
    public void clienteMasInventario_errorAlGuardar() {
        when(repo.save(any(Cliente.class)))
            .thenThrow(new RuntimeException("Error al guardar cliente"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            clienteService.clienteMasInventario(clienteDTO);
        });

        assertEquals("Error al guardar cliente", exception.getMessage());
    }

    @Test
    public void borrarClientePorId_exitoso() {
        doNothing().when(repo).deleteById(1);

        clienteService.borrarClientePorId(1);

        verify(repo, times(1)).deleteById(1);
    }

   @Test
    public void borrarClientePorId_noEncontrado() {
        doThrow(new RuntimeException("Cliente no encontrado"))
            .when(repo).deleteById(99);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            clienteService.borrarClientePorId(99);
        });

        assertEquals("Cliente no encontrado", exception.getMessage());
    }

    @Test
    public void agregarProductos_exitoso() {
        when(repo.findById(1)).thenReturn(Optional.of(clienteEjem));
        when(repoInv.saveAll(anyList())).thenReturn(List.of(inventarioEjem));

        clienteService.agregarProductos(1, List.of(inventarioDTO));

        verify(repoInv, times(1)).saveAll(anyList());
    }

    @Test
    public void agregarProductos_clienteNoRegistrado() {
        when(repo.findById(99)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            clienteService.agregarProductos(99, List.of(inventarioDTO));
        });

        assertEquals("Cliente no registrado ", exception.getMessage());
    }

    @Test
    public void agregarProductos_listaVacia() {
        when(repo.findById(1)).thenReturn(Optional.of(clienteEjem));
        when(repoInv.saveAll(anyList())).thenReturn(List.of());

        clienteService.agregarProductos(1, List.of());

        verify(repoInv, times(1)).saveAll(anyList());
    }
    @Test
    public void obtenerInventarioPorCliente_exitoso() {
        when(repoInv.findByClienteId(1)).thenReturn(List.of(inventarioEjem));

        List<InventarioCliente> resultado = clienteService.obtenerInventarioPorCliente(1);

        assertEquals(1, resultado.size());
        assertEquals(inventarioEjem.getNombre(), resultado.get(0).getNombre());
    }
 
    @Test
    public void obtenerInventarioPorCliente_vacio() {
        when(repoInv.findByClienteId(99)).thenReturn(List.of());

        List<InventarioCliente> resultado = clienteService.obtenerInventarioPorCliente(99);

        assertTrue(resultado.isEmpty());
    }
}