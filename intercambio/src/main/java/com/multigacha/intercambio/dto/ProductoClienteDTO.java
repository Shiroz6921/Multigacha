package com.multigacha.intercambio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoClienteDTO {
    private Integer id;
    private ClienteDTO cliente;
    private ProductoDTO producto;
}
