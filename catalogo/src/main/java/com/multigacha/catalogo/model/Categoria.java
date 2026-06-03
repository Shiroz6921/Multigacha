package com.multigacha.catalogo.model;


import java.util.ArrayList;
import java.util.List;

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
public class Categoria {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false)
    private String coleccion;

    @Column(nullable = false)
    private String franquicia;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)   
    private List<Producto> productos = new ArrayList<>();
}