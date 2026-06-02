package com.multigacha.intercambio.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.multigacha.intercambio.dto.ProductoDTO;

@FeignClient(name = "producto", url = "http://localhost:3308")
public interface ProductoClient {
    @GetMapping("api/v1/productos/{id}")
    ProductoDTO buscarDTO(@PathVariable("id") Integer id);
}
