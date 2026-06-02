package com.multigacha.intercambio.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multigacha.intercambio.client.ClienteClient;
import com.multigacha.intercambio.client.ProductoClient;
import com.multigacha.intercambio.dto.ClienteDTO;
import com.multigacha.intercambio.dto.ProductoDTO;
import com.multigacha.intercambio.model.Intercambio;
import com.multigacha.intercambio.model.ProductoCliente;
import com.multigacha.intercambio.repo.IntercambioRepo;
import com.multigacha.intercambio.repo.ProductoClienteRepo;

@Service
public class IntercambioService {
    @Autowired
    private IntercambioRepo repo1;
    @Autowired
    private ProductoClienteRepo repo2;
    @Autowired
    private ClienteClient cliente;
    @Autowired
    private ProductoClient producto;

    public List<Intercambio> listarIntercambios() {
        return repo1.findAll();
    }

    public List<ProductoCliente> listarInventarios() {
        return repo2.findAll();
    }

    public List<ProductoCliente> listarInventariosPorCliente(Integer id) {
        return repo2.findByIdCliente(id);
    }

    public ProductoCliente mostrarInventarioPorProducto(Integer id) {
        return repo2.findByIdProducto(id);
    }

    public Intercambio buscarIntercambioPorId(Integer id) {// desde empleado
        return repo1.findById(id).orElseThrow(() -> new RuntimeException("Intercambio no encontrado"));
    }

    public ProductoCliente buscarInventarioPorId(Integer id) {
        return repo2.findById(id).orElseThrow(() -> new RuntimeException("Inventario no encontrado"));
    }

    public ProductoCliente crearInventario(Integer idCliente, Integer idProducto) {
        ClienteDTO clienteDTO = cliente.buscarDTO(idCliente);
        ProductoDTO productoDTO = producto.buscarDTO(idProducto);
        ProductoCliente inventario = new ProductoCliente();
        inventario.setIdCliente(clienteDTO.getId());
        inventario.setIdProducto(productoDTO.getId());
        return inventario;
    }

    public ProductoCliente buscarInventario(Integer idCliente1, Integer idProducto) {
        List<ProductoCliente> inventarioClientex = repo2.findByIdCliente(idCliente1);
        ProductoCliente inventarioProducto = repo2.findByIdProducto(idProducto);
        for (ProductoCliente inventario : inventarioClientex) {
            if (inventario.getId() == inventarioProducto.getId()) {
                return inventario;
            }
        }
        return null;
    }

    public Intercambio crearIntercambio(Integer idProducto, Integer idCliente1, Integer idCliente2) {
        ClienteDTO clienteDTO1 = cliente.buscarDTO(idCliente1);
        ClienteDTO clienteDTO2 = cliente.buscarDTO(idCliente2);
        ProductoDTO productoDTO = producto.buscarDTO(idProducto);
        ProductoCliente inventarioCliente1 = buscarInventario(clienteDTO1.getId(), productoDTO.getId());
        ProductoCliente inventarioCliente2 = new ProductoCliente();
        inventarioCliente2.setIdCliente(clienteDTO2.getId());
        inventarioCliente2.setIdProducto(productoDTO.getId());
        inventarioCliente1.setIdCliente(null);
        inventarioCliente1.setIdProducto(null);
        Intercambio intercambio = new Intercambio();
        intercambio.setFecha(new Date());
        intercambio.setTraspaso(inventarioCliente2);
        return intercambio;
    }

    // acciones de admin. NO TOCAR
    public void eliminarInventario(Integer id) {
        repo2.deleteById(id);
    }

    public void eliminarIntercambio(Integer id) {
        repo1.deleteById(id);
    }

}
