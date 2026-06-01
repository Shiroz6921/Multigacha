package com.multigacha.compra.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "intercambio-service", url = "http://localhost:3307/api/v1/intercambios")
public interface IntercambioClient {
    @PutMapping("/transferir/{idVendedor}/{idComprador}/{idProducto}")
    void transferirCarta(@PathVariable("idVendedor") Integer idVendedor, 
                         @PathVariable("idComprador") Integer idComprador, 
                         @PathVariable("idProducto") Integer idProducto);
}