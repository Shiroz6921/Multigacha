package com.multigacha.modificacion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactoDTO {
    private Integer id;
    private String telefono;
    private String direccion;

}
