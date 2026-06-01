package com.multigacha.compra.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "catalogo-service", url = "http://localhost:8081/api/v1/catalogo")
public interface CatalogoClient {
    
    @PutMapping("/{id}/reducir-stock/{cantidad}")
    void reducirStock(@PathVariable("id") Integer id, @PathVariable("cantidad") Integer cantidad);
}