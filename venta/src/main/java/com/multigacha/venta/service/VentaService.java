package com.multigacha.venta.service;

import com.multigacha.venta.client.CatalogoClient;
import com.multigacha.venta.client.ClienteClient;
import com.multigacha.venta.client.IntercambioClient;
import com.multigacha.venta.dto.ProductoClienteDTO;
import com.multigacha.venta.model.Venta;
import com.multigacha.venta.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VentaService {

    @Autowired
    private VentaRepository repo;

    @Autowired
    private ClienteClient clienteClient;

    @Autowired
    private CatalogoClient catalogoClient;

    @Autowired
    private IntercambioClient intercambioClient;

    public Venta publicarCarta(Venta venta) {
        if (clienteClient.buscarPorId(venta.getIdVendedor()) == null) {
            throw new RuntimeException("El vendedor no existe.");
        }
        if (catalogoClient.buscarProductoPorId(venta.getIdProducto()) == null) {
            throw new RuntimeException("El producto no existe en el catálogo.");
        }

        List<ProductoClienteDTO> inventarioDelJugador = intercambioClient.listarInventariosPorCliente(venta.getIdVendedor());
        
        boolean poseeLaCarta = inventarioDelJugador.stream()
                .filter(item -> item.getIdProducto() != null) // Evitamos nulos
                .anyMatch(item -> item.getIdProducto().equals(venta.getIdProducto()));

        if (!poseeLaCarta) {
            throw new RuntimeException("Error: El jugador no posee esta carta en su inventario para venderla.");
        }

        venta.setEstado("DISPONIBLE");
        return repo.save(venta);
    }

    public Venta obtenerVentaPorId(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Publicación no encontrada"));
    }

    public void marcarComoVendida(Integer id) {
        Venta venta = obtenerVentaPorId(id); 
        venta.setEstado("VENDIDA");
        repo.save(venta);
    }
}