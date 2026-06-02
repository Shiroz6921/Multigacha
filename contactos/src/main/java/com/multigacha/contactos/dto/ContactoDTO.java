package com.multigacha.contactos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactoDTO {
    private Integer id;
    private Integer telefono;
    private String direccion;

}
