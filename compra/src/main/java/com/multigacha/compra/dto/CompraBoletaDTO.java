package com.multigacha.compra.dto;

import com.multigacha.compra.model.Compra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompraBoletaDTO {

    private Compra compra;
    private BoletaDTO boletaDTO;

}
