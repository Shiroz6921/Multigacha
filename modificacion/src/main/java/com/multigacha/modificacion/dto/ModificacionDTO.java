package com.multigacha.modificacion.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModificacionDTO {
    private Integer id;
    private Date fecha;
    private EmpleadoDTO idEmpleado;
    private ProductoDTO idProducto;

}
