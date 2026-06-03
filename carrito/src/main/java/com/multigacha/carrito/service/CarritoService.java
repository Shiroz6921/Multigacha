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

@Service
public class CarritoService {


    @Autowired
    private CarritoRepository repository;

    @Autowired
    private CatalogoClient catalogoClient;

    @Autowired
    private ClientesClient cliente;

   public Carrito crearCarrito(Integer id) {
        ClienteDTO clienteDTO = cliente.buscarPorId(id);
        Carrito carrito = new Carrito();
        carrito.setClienteId(clienteDTO.getId());
        carrito.setTotal(0.0);
        return repository.save(carrito);
    }

    public Carrito agregarProducto(Integer carritoId, CarritoDTO dto) {
        ProductoDTO prodDto = catalogoClient.buscarProductoPorId(dto.getProductoId());

        Carrito carrito = repository.findById(carritoId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        ProductosCarritos nuevoItem = new ProductosCarritos();
        nuevoItem.setProductoId(prodDto.getId());
        nuevoItem.setNombre(prodDto.getNombre());
        nuevoItem.setPrecio(prodDto.getPrecio());
        nuevoItem.setCantidad(dto.getCantidad());

        carrito.getItems().add(nuevoItem);

        double total = 0.0;
    
        for (ProductosCarritos item : carrito.getItems()) {
            double subtotal = item.getPrecio() * item.getCantidad();
            total = total + subtotal;
        }
        carrito.setTotal(total);
        return repository.save(carrito);
    }
    public List<Carrito> listarTodos() {
        return repository.findAll();
    }
    
    public Carrito buscarId(Integer id){
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
    }
}
