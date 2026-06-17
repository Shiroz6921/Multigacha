package com.multigacha.modificacion.model;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "modificacion")
@Schema(description = "Representa una modificación realizada en el sistema, incluyendo la fecha de la modificación, el empleado responsable y el producto modificado.")
public class Modificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la modificación, generado automáticamente.", example = "1")
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "Fecha de la modificación.", example = "2023-10-10")
    private Date fecha;

    @ManyToOne
    @JoinColumn(name = "empleado_id")
    @Schema(description = "Empleado responsable de la modificación.", example = "1")
    private Empleado empleado;

    @Column(name = "producto_id")
    @Schema(description = "ID del producto modificado.", example = "1")
    private Integer idProducto;
}
