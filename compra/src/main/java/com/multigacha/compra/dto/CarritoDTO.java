package com.multigacha.compra.dto;

import java.util.List;


import lombok.Data;

@Data
public class CarritoDTO {
    private Integer id;
    private Double total;
    private List<ProductoCarritoDTO> items;
}