package com.multigacha.clientes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactoDTO {
    /*lo mismo que el el clienteDTO 
    private Integer id;*/

    //private String correo; //no hay un dato correo en el ms contacto

    private Integer telefono;
    private String direccion;
}
