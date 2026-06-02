package com.multigacha.catalogo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.multigacha.catalogo.dto.EmpleadoDTO;

@FeignClient(name = "empleado", url = "")
public interface EmpleadoClient {
    @GetMapping("api/v1/empleado/{id}")
    EmpleadoDTO buscarDTO(Integer id);

}
