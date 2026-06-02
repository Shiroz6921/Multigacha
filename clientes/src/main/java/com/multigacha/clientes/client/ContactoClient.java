package com.multigacha.clientes.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.multigacha.clientes.dto.ContactoDTO;

@FeignClient(name = "contactos", url = "http://localhost:8086/api/v1/contactos")
public interface ContactoClient {
    @GetMapping("/{id}")
    ContactoDTO buscarContacto(@PathVariable("id") Integer id);
}
