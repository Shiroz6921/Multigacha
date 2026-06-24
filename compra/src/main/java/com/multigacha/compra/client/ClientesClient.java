package com.multigacha.compra.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.multigacha.compra.dto.InventarioDTO;

@FeignClient(name = "clientesMS",url = "http://localhost:8083/api/v1/clientes")
public interface ClientesClient {

    
    @PostMapping("/inventario/{idCliente}")
    void agregarProductosAlInventario(@PathVariable("idCliente") Integer idCliente, 
                                      @RequestBody List<InventarioDTO> productos);
}
