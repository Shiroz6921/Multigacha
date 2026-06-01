package com.multigacha.compra.dto;

import lombok.Data;

@Data
public class ProductoCarritoDTO {
    private Integer id;
    private Integer productoId;
    private String nombre;
    private Double precio;
    private Integer cantidad;
}