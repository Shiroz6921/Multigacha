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
    public Compra getCompra() { return compra; }
    public void setCompra(Compra compra) { this.compra = compra; }

    public BoletaDTO getBoletaDTO() { return boletaDTO; }
    public void setBoletaDTO(BoletaDTO boletaDTO) { this.boletaDTO = boletaDTO; }
}
