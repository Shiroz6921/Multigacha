package com.multigacha.compra.client;

import com.multigacha.compra.dto.VentaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "venta-service", url = "http://localhost:8086/api/v1/ventas")
public interface VentaClient {
    @GetMapping("/{id}")
    VentaDTO obtenerVentaPorId(@PathVariable("id") Integer id);

    @PutMapping("/{id}/vender")
    void marcarComoVendida(@PathVariable("id") Integer id);
}