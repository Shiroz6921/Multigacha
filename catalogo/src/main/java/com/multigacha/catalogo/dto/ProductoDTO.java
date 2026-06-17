package com.multigacha.catalogo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ProductoDTO {
    @Schema(description = "Nombre del producto.",example = ("Pikachu ex"))
    private String nombre;
    @Schema(description = "Precio del producto.",example = ("19.99"))
    private Double precio;
    @Schema(description = "Stock del producto.",example = ("10"))
    private Integer stock;
    @Schema(description = "ID de la categoria a la que pertenece el producto.",example = ("1"))
    private Integer categoriaId;
}