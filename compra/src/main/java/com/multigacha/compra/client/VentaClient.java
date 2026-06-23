package com.multigacha.compra.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "venta-service", url = "http://localhost:8086/api/v1/ventas")
public interface VentaClient {

    @PutMapping("/{id}/vender")
    void marcarComoVendida(@PathVariable("id") Integer id);
}