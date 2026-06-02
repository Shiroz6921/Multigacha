package com.multigacha.catalogo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.multigacha.catalogo.dto.ModificacionDTO;

@FeignClient(name = "modificacion", url = "")
public interface ModificacionClient {
    @GetMapping("api/v1/modificacion/{id}")
    ModificacionDTO buscarDTO(Integer id);

}
