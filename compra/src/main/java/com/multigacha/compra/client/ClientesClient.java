package com.multigacha.compra.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.multigacha.compra.dto.ClienteDTO;

@FeignClient(name = "clientes",url = "http://localhost:8080/api/v1/clientes")
public interface ClientesClient {
    @GetMapping("/{id}")
    public ClienteDTO buscarPorId(@PathVariable("id") Integer id);
}
