package com.multigacha.carrito.dto;

import lombok.Data;

@Data
public class ProductoDTO {
    private Integer id;
    private String nombre;
    private double precio;
    private Integer stock;
}
