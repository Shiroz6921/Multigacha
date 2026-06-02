package com.multigacha.intercambio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDTO {
    private Integer id;
    private String nombre;
    private double precio;
    private Integer categoriaId;
}
