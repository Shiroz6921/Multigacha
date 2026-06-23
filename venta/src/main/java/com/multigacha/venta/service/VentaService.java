package com.multigacha.venta.service;

import com.multigacha.venta.client.CatalogoClient;
import com.multigacha.venta.client.ClienteClient;
import com.multigacha.venta.client.IntercambioClient;
import com.multigacha.venta.dto.ProductoClienteDTO;
import com.multigacha.venta.model.Venta;
import com.multigacha.venta.repository.VentaRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VentaService {

    @Autowired
    private VentaRepository repo;
    @Autowired
    private ClienteClient clienteClient;
    @Autowired
    private CatalogoClient catalogoClient;

    public Venta publicarCarta(Venta venta) {
        
        clienteClient.buscarPorId(venta.getIdVendedor());
        catalogoClient.buscarProductoPorId(venta.getIdProducto());
        List<ProductoClienteDTO> inventarioDelJugador = 
        clienteClient.obtenerInventario(venta.getIdVendedor());

        boolean poseeLaCarta = false;
        for (ProductoClienteDTO item : inventarioDelJugador) {
            if (item.getIdProducto().equals(venta.getIdProducto())) {
                poseeLaCarta = true;
                break;
            }
        }
        
        if (!poseeLaCarta) {
            throw new RuntimeException("Error: El jugador no posee esta carta en su inventario para venderla.");
        }

        venta.setEstado("DISPONIBLE");
        return repo.save(venta);
    }

    public Venta obtenerVentaPorId(Integer id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Publicación no encontrada"));
    }

    public void marcarComoVendida(Integer id) {
        Venta venta = obtenerVentaPorId(id);
        venta.setEstado("VENDIDA");
        repo.save(venta);
    }
}
