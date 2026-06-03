package com.multigacha.modificacion.model;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
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
public class Modificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @JsonFormat(pattern = "DD/MM/YYYY", timezone = "GMT-4")
    @Column(nullable = false)
    private Date fecha;

    @ManyToOne
    @JoinColumn(name = "empleado_id")
    private Empleado empleado;

    @Column(name = "producto_id")
    private Integer idProducto;
}
