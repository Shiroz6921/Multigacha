package com.multigacha.compra.dto;

import lombok.Data;

@Data
public class BoletaDTO {
    private Integer idComprador;
    private Integer idVendedor;
    private Double montoTotal;
    private String detalle;
}