package com.multigacha.intercambio.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.multigacha.intercambio.dto.ClienteDTO;

@FeignClient(name = "clientes", url = "http://localhost:3306")
public interface ClienteClient {
    @GetMapping("api/v1/clientes/{id}")
    ClienteDTO buscarDTO(@PathVariable("id") Integer id);

}
