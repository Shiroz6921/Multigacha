package com.multigacha.contactos.model;

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
@Table(name = "contacto")
@Schema(description = "Representa la información de contacto de un cliente, incluyendo su número de teléfono y dirección.")
public class Contacto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del contacto", example = "1")
    private Integer id;

    @Column(nullable = false, length = 8)
    @Schema(description = "Número de teléfono del cliente", example = "12345678")
    private Integer telefono;

    @Column
    @Schema(description = "Dirección del cliente", example = "Calle Principal 123")
    private String direccion;

}
