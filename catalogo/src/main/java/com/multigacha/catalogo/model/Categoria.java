package com.multigacha.catalogo.model;


import java.util.ArrayList;
import java.util.List;

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
@Table(name = "categoria")
@Schema(description = "Respresenta la coleccion y franquicia de las cartas.")
public class Categoria {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    @Schema(description = "ID unico de la categoria.",example = ("1"))
    private Integer id;
    
    @Column(nullable = false)
    @Schema(description = "Nombre de la coleccion.",example = ("Pokémon"))
    private String coleccion;

    @Column(nullable = false)
    @Schema(description = "Nombre de la franquicia.",example = ("Espada y Escudo"))
    private String franquicia;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)   
    private List<Producto> productos = new ArrayList<>();
}