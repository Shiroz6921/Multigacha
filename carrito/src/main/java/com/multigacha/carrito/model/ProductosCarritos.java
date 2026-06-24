package com.multigacha.carrito.model;

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
@Table(name = "productos_carrito")
@Schema(description = "Representa los productos añadidos al carrito de compras, incluyendo detalles como el ID del producto, nombre, cantidad y precio.")
public class ProductosCarritos {

    @Id
    @Column(name = "productos_carrito_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del producto en el carrito, generado automáticamente.", example = "1")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "carrito_id")
    @JsonIgnore
    @Schema(description = "El carrito al que pertenece este producto.")
    private Carrito carrito;

    @Schema(description = "ID del producto.", example = "101")
    private Integer productoId;

    @Schema(description = "Nombre del producto.", example = "Pikachu ex")
    private String nombre;

    @Schema(description = "Cantidad del producto en el carrito.", example = "2")
    private Integer cantidad;

    @Schema(description = "Precio unitario del producto.", example = "999.99")
    private Double precio;
}