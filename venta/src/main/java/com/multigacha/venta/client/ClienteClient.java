package com.multigacha.venta.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.multigacha.venta.dto.ProductoClienteDTO;

@FeignClient(name = "clientesMS", url = "http://localhost:8083/api/v1/clientes")
public interface ClienteClient {

    @GetMapping("/{id}")
    void buscarPorId(@PathVariable Integer id); // lanza excepción si no existe

    @GetMapping("/inventario/{idCliente}")
    List<ProductoClienteDTO> obtenerInventarioPorCliente(@PathVariable Integer idCliente);
}