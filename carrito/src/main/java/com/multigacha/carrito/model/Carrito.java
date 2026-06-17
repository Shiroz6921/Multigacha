package com.multigacha.carrito.model;


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

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "carrito")
@Schema(description = "Representa el carrito de compras de un cliente, incluyendo el ID del cliente, el total acumulado y los productos añadidos al carrito.")
public class Carrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del carrito, generado automáticamente.", example = "1")
    private Integer id;
    
    @Column(nullable = false)
    @Schema(description = "ID del cliente al que pertenece el carrito.", example = "1001")
    private Integer clienteId;

    @Column(nullable = false)
    @Schema(description = "Total acumulado en el carrito.", example = "150.00")
    private Double total;

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Lista de productos incluidos en el carrito.")
    private List<ProductosCarritos> items = new ArrayList<>();
}