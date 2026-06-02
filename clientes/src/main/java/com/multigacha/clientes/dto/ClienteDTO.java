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
    /*no es necesario tener el id porque se genera auto 
    private Integer id;*/
    
    /*NUEVO*/private String run;
    private String nombre;
    private String apellido;
    /*lo mismo que en el model 
    private Date fechNac;*/   
    private LocalDate fechNac;
    private List<InventarioDTO> inventarios = new ArrayList<>() ;
    private ContactoDTO contacto;
}
