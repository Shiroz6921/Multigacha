package com.multigacha.clientes.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cliente")
@Schema(description = "Representa un cliente, incluyendo su información personal y su inventario de productos.")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del cliente, generado automáticamente.", example = "1")
    private Integer id;

    @Column(unique = true, length = 13, nullable = false)
    @Schema(description = "RUN del cliente.", example = "12345678-9")
    private String run;

    @Column(length = 40, nullable = false)
    @Schema(description = "Nombre del cliente.", example = "Juan")
    private String nombre;

    @Column(length = 40, nullable = false)
    @Schema(description = "Apellido del cliente.", example = "Pérez")
    private String apellido;

    @CreationTimestamp
    @Column(nullable = false) 
    @Schema(description = "Fecha de nacimiento del cliente.", example = "1990-01-01")
    private LocalDate fechNac;


    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Lista de inventarios del cliente.")
    private List<InventarioCliente> inventarios = new ArrayList<>();
}
