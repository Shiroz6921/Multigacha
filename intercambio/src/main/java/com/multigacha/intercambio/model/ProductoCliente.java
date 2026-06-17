package com.multigacha.intercambio.model;

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
@Table(name = "producto_cliente")
public class ProductoCliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del producto cliente, generado automáticamente.", example = "1")
    private Integer id;
    @Column(name = "cliente_id")
    @Schema(description = "ID del cliente al que pertenece el producto.", example = "1")
    private Integer idCliente;
    @Column(name = "producto_id")
    @Schema(description = "ID del producto.", example = "1")
    private Integer idProducto;

}
