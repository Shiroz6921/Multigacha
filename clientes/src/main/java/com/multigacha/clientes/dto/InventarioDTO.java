package com.multigacha.clientes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//NUEVO
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventarioDTO {
    
    private Integer cantidadProd;

    private String nombre;
}
