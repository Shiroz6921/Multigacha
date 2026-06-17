package com.multigacha.torneo.model;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Entity
@Table(name = "inscripciones")
@AllArgsConstructor
@NoArgsConstructor
public class inscripciones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la inscripción, generado automáticamente.", example = "1")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "torneo_id")
    @Schema(description = "Torneo al que se inscribe el participante.")
    private torneo torneo;

    @Schema(description = "ID del cliente que se inscribe.", example = "1")
    private Integer clienteId;

    @Schema(description = "Nombre del participante.", example = "Juan Pérez")
    private String nombre;
}
