package com.multigacha.compra.service;

import com.multigacha.compra.model.Compra;
import com.multigacha.compra.repository.CompraRepository;
import com.multigacha.compra.client.*;
import com.multigacha.compra.dto.*;

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
    @Autowired private IntercambioClient intercambioClient;

    public CompraBoletaDTO procesarCompraDesdeCarrito(Integer idComprador, Integer idCarrito) {
        
        CarritoDTO carrito = carritoClient.buscarPorId(idCarrito);

        if(carrito.getItems() == null || carrito.getItems().isEmpty()) {
            throw new RuntimeException("El carrito está vacío.");
        }

        for (ProductoCarritoDTO item : carrito.getItems()) {
            
            catalogoClient.reducirStock(item.getProductoId(), item.getCantidad());

            for (int i = 0; i < item.getCantidad(); i++) {
                intercambioClient.transferirCarta(0, idComprador, item.getProductoId()); 
            }
        }

        BoletaDTO boleta = new BoletaDTO();
        boleta.setIdComprador(idComprador);
        boleta.setIdVendedor(0);
        boleta.setMontoTotal(carrito.getTotal());
        boleta.setDetalle("Compra desde Carrito ID: " + idCarrito + " con " + carrito.getItems().size() + " productos distintos.");
        BoletaDTO boletaGenerada = boletaClient.generarBoleta(boleta);


        Compra compra = new Compra();
        compra.setIdComprador(idComprador);
        compra.setIdVenta(idCarrito); 
        compra.setTotalPagado(carrito.getTotal());
        compra.setFechaCompra(new Date());

    Compra compraGuardada = repo.save(compra);   
    CompraBoletaDTO c1 = new CompraBoletaDTO(compraGuardada, boletaGenerada);

    return c1;
    }
    public List<Compra> listar(){
        return repo.findAll();
    }
}