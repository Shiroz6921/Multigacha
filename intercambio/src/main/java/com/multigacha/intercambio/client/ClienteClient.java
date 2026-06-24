package com.multigacha.intercambio.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.multigacha.intercambio.dto.ClienteDTO;

@FeignClient(name = "clientesMS",url = "http://localhost:8083/api/v1/clientes")
public interface ClienteClient {
    @GetMapping("api/v1/clientes/{id}")
    ClienteDTO buscarPorId(@PathVariable("id") Integer id);

}
