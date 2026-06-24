package com.multigacha.venta.dto;
import lombok.Data;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductoClienteDTO {
    private Integer id;
    private Integer cantidadProd;
    private String nombre;
}