package com.multigacha.clientes.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.multigacha.clientes.dto.ContactoDTO;

import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "contactosMS")
public interface ContactoClient {
    @PostMapping("/add")
    void agregarContacto(@RequestBody ContactoDTO contacto);
}
