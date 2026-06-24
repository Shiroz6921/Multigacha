package com.multigacha.clientes.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {

    private String run;
    private String nombre;
    private String apellido; 
    
    private LocalDate fechNac;
    private List<InventarioDTO> inventarios = new ArrayList<>() ;
    private ContactoDTO contacto;
}
