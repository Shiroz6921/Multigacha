package com.multigacha.carrito.service;

import com.multigacha.carrito.client.ClientesClient;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multigacha.carrito.client.CatalogoClient;

import com.multigacha.carrito.dto.CarritoDTO;
import com.multigacha.carrito.dto.ClienteDTO;
import com.multigacha.carrito.dto.ProductoDTO;
import com.multigacha.carrito.model.Carrito;
import com.multigacha.carrito.model.ProductosCarritos;
import com.multigacha.carrito.repository.CarritoRepository;
import com.multigacha.carrito.repository.ProductosCarritoRepository;

@Service
public class CarritoService {


    @Autowired
    private CarritoRepository repo1;

    @Autowired
    private CatalogoClient catalogoClient;

    @Autowired
    private ProductosCarritoRepository repo2;
    @Autowired
    private ClientesClient cliente;



    public Carrito crearCarrito(Integer id) {
        ClienteDTO clienteDTO = cliente.buscarPorId(id);
        Carrito carrito = new Carrito();
        carrito.setClienteId(clienteDTO.getId());
        carrito.setTotal(0.0);
        return repo1.save(carrito);
    }

    public ProductosCarritos agregarProducto(Integer carritoId, CarritoDTO dto) {
        ProductoDTO prodDto = catalogoClient.buscarProductoPorId(dto.getProductoId());

        Carrito carrito = repo1.findById(carritoId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
        ProductosCarritos nuevoItem = new ProductosCarritos();

        nuevoItem.setProductoId(prodDto.getId());
        nuevoItem.setNombre(prodDto.getNombre() ); 
        nuevoItem.setPrecio(prodDto.getPrecio());
        nuevoItem.setCarrito(carrito);
        if (prodDto.getStock()< dto.getCantidad()) {
            throw new RuntimeException("no hay suficientes cartas");
        }
        nuevoItem.setCantidad(dto.getCantidad());
        repo2.save(nuevoItem);
        
        double total = 0.0;
        List<ProductosCarritos> productosDelCarrito = repo2.findByCarrito(carrito);

        for (ProductosCarritos productosCarritos : productosDelCarrito ) {
            double totalFinal =productosCarritos.getPrecio() * productosCarritos.getCantidad();
            total = total + totalFinal;
        }
        carrito.setTotal(total);
        repo1.save(carrito);
        return nuevoItem;
        }

    public List<ProductosCarritos> obtenerTodo() {
        return repo2.findAll();
    }

    public ProductosCarritos buscarPorId(Integer id) {
        return repo2.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    public List<ProductosCarritos> buscarCarrito(Integer carritoId) {
        Carrito carrito = new Carrito();
        carrito.setId(carritoId);
        
        return repo2.findByCarrito(carrito);
    }
}

