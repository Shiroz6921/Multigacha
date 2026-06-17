package com.multigacha.catalogo.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

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

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "producto")
public class Producto {
    @Id
    @Column(name ="producto_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID unico del producto.",example = ("1"))
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "Nombre del producto.",example = ("Pikachu ex"))
    private String nombre;

    @Column(nullable = false)
    @Schema(description = "Precio del producto.",example = ("19.99"))
    private double precio;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    @JsonIgnore
    private Categoria categoria;


    @Column (nullable = false)
    @Schema(description = "Stock del producto.",example = ("10"))
    private Integer stock;
}