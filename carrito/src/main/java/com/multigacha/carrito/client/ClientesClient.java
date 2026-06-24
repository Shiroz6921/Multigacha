package com.multigacha.carrito.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.multigacha.carrito.dto.ClienteDTO;

@FeignClient(name = "clientesMS",url = "http://localhost:8083/api/v1/clientes")
public interface ClientesClient {
    @GetMapping("/{id}")
    public ClienteDTO buscarPorId(@PathVariable("id") Integer id);
}
