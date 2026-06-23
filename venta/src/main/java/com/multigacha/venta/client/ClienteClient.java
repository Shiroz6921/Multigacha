package com.multigacha.venta.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.multigacha.venta.dto.ClienteDTO;
import com.multigacha.venta.dto.ProductoClienteDTO;

// Apunta al puerto 3306 de tu microservicio de Clientes
@FeignClient(name = "clientes", url = "http://localhost:8080/api/v1/clientes")
public interface ClienteClient {
    @GetMapping("/{id}")
    ClienteDTO buscarPorId(@PathVariable("id") Integer id);

    @GetMapping("/inventario/{idCliente}")
    List<ProductoClienteDTO> obtenerInventario(@PathVariable("idCliente") Integer idCliente);
}