package com.multigacha.modificacion.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.multigacha.modificacion.dto.ContactoDTO;

@FeignClient(name = "contacto", url = "http://localhost:3305")
public interface ContactoClient {
    @GetMapping("api/v1/contacto/{id}")
    ContactoDTO buscarDTO(@PathVariable("id") Integer id);

}
