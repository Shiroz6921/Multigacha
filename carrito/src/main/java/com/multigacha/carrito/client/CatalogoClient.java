package com.multigacha.carrito.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.multigacha.carrito.dto.ProductoDTO;

@FeignClient(name = "catalogoMS", url = "http://localhost:8089/api/v1/catalogo")
public interface CatalogoClient {
    @GetMapping("/{id}")
    public ProductoDTO buscarProductoPorId(@PathVariable("id") Integer id);
}
