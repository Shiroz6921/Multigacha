package com.multigacha.venta.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.multigacha.venta.dto.ProductoDTO;

@FeignClient(name = "catalogoMS", url = "http://localhost:8089/api/v1/catalogo")
public interface CatalogoClient {
    @GetMapping("/{id}")
    ProductoDTO buscarProductoPorId(@PathVariable("id") Integer id);
}