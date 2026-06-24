package com.multigacha.compra.client;

import com.multigacha.compra.dto.CarritoDTO;
import com.multigacha.compra.dto.ProductoCarritoDTO;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "carritoMS", url = "http://localhost:8090/api/v1/carrito")
public interface CarritoClient {
    @GetMapping("/{id}")
    CarritoDTO buscarPorId(@PathVariable("id") Integer id);
    
    @GetMapping("/carrito/{carritoId}")
    List<ProductoCarritoDTO> buscarProductosPorCarritoId(@PathVariable("carritoId")Integer id);
}