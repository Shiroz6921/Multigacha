package com.multigacha.torneo.model;

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
@Entity
@Table(name = "torneo")
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Representa un torneo en el sistema, incluyendo su nombre y el número máximo de participantes.")
public class torneo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del torneo, generado automáticamente.", example = "1")
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "Nombre del torneo.", example = "Pokemon Championship")
    private String nombre;
    
    @Column(nullable = false)
    @Schema(description = "Número máximo de participantes en el torneo.", example = "16")
    private Integer maxParticipantes;
}
