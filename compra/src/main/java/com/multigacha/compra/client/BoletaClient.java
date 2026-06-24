package com.multigacha.compra.client;

import com.multigacha.compra.dto.BoletaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "boletaMS", url = "http://localhost:8091/api/v1/boletas")
public interface BoletaClient {
    @PostMapping("/generar")
    BoletaDTO generarBoleta(@RequestBody BoletaDTO boleta);
}