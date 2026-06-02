package com.multigacha.catalogo.dto;

import lombok.Data;

@Data
public class ProductoDTO {
    private Integer productoId;
    private String nombre;
    private Double precio;
    private Integer stock;
    private Integer categoriaId;
}