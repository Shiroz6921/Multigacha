package com.multigacha.modificacion.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "empleado")
@Schema(description = "Representa un empleado del sistema, incluyendo su nombre, apellido y contacto.")
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del empleado, generado automáticamente.", example = "1")
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "Nombre del empleado.", example = "Juan")
    private String nombre;

    @Column(nullable = false)
    @Schema(description = "Apellido del empleado.", example = "Pérez")
    private String apellido;

    @Column(name = "contacto_id", nullable = false)
    @Schema(description = "ID del contacto del empleado.", example = "1")
    private Integer idContacto;
}
