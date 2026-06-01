package com.multigacha.compra.dto;

import lombok.Data;

@Data
public class VentaDTO {
    private Integer id;
    private Integer idVendedor;
    private Integer idProducto;
    private Double precio;
    private String estado;
}
