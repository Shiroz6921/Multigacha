package com.multigacha.intercambio.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class IntercambioRequestDTO {
    @Schema(description = "ID del producto cliente A involucrado en el intercambio.", example = "1")
    private Integer idProductoA;
    @Schema(description = "ID del producto cliente B involucrado en el intercambio.", example = "2")
    private Integer idProductoB;
}
