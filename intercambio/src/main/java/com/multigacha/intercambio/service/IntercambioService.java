package com.multigacha.intercambio.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.multigacha.intercambio.dto.IntercambioRequestDTO;
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

    public Intercambio crearIntercambio (IntercambioRequestDTO dto) {
        ProductoCliente inventarioA = repo2.findByIdProducto(dto.getIdProductoA());
        ProductoCliente inventarioB = repo2.findByIdProducto(dto.getIdProductoB());
        if (inventarioA == null || inventarioB == null){
            throw new RuntimeException("Uno o ambos productos no se encuentran");
        }
        Integer clienteTemp = inventarioA.getIdCliente();
        inventarioA.setIdCliente(inventarioB.getIdCliente());
        inventarioB.setIdCliente(clienteTemp);
        repo2.save(inventarioB);  
        repo2.save(inventarioA);  
        Intercambio intercambio = new Intercambio();
        intercambio.setFecha(new Date());
        intercambio.setTraspaso(inventarioA);
        repo1.save(intercambio); 
        return intercambio;
    }

    public void eliminarIntercambio(Integer id) {
        repo1.deleteById(id);
    }

    public List<Intercambio> obtenerTodos() {
        return repo1.findAll();
    }

}