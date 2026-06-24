package com.multigacha.intercambio.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.multigacha.intercambio.dto.ProductoDTO;

@FeignClient(name = "catalogoMS", url = "http://localhost:8089/api/v1/catalogo")
public interface ProductoClient {
    @GetMapping("api/v1/productos/{id}")
    ProductoDTO buscarProductoPorId(@PathVariable("id") Integer id);
}
