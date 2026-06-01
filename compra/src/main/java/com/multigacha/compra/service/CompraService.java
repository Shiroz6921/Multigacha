package com.multigacha.compra.service;

import com.multigacha.compra.model.Compra;
import com.multigacha.compra.repository.CompraRepository;
import com.multigacha.compra.client.*;
import com.multigacha.compra.dto.*;

import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class CompraService {

    @Autowired private CompraRepository repo;
    
    @Autowired private CarritoClient carritoClient;
    @Autowired private CatalogoClient catalogoClient;
    @Autowired private BoletaClient boletaClient;

    public CompraBoletaDTO procesarCompraDesdeCarrito(Integer idComprador, Integer idCarrito) {
    List<ProductoCarritoDTO> items = carritoClient.buscarProductosPorCarritoId(idCarrito);

    if (items == null || items.isEmpty()) {
        throw new RuntimeException("El carrito está vacío.");
    }

    double total = 0.0;

    for (ProductoCarritoDTO item : items) {
        catalogoClient.reducirStock(item.getProductoId(), item.getCantidad());
        total += (item.getPrecio() * item.getCantidad());
    }

        BoletaDTO boleta = new BoletaDTO();
        boleta.setIdComprador(idComprador);
        boleta.setIdVendedor(0);
        boleta.setMontoTotal(total);
        boleta.setDetalle("Compra desde Carrito ID: " + idCarrito + " con " + items.size() + " productos distintos.");
        BoletaDTO boletaGenerada = boletaClient.generarBoleta(boleta);

    Compra compra = new Compra();
    compra.setIdComprador(idComprador);
    compra.setIdVenta(idCarrito);

    compra.setTotalPagado(total);
    compra.setFechaCompra(new Date());

    Compra compraGuardada = repo.save(compra);   
    CompraBoletaDTO c1 = new CompraBoletaDTO(compraGuardada, boletaGenerada);

    return c1;
}
    public List<Compra> listar(){
        return repo.findAll();
    }
}