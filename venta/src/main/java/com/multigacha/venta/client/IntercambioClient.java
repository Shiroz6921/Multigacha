package com.multigacha.venta.client;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.multigacha.venta.dto.ProductoClienteDTO;

@FeignClient(name = "intercambio-service", url = "http://localhost:3307/api/v1/intercambios")
public interface IntercambioClient {
    @GetMapping("/inventarios/cliente/{id}")
    List<ProductoClienteDTO> listarInventariosPorCliente(@PathVariable("id") Integer id);
}