package com.multigacha.clientes.model;

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
@Data
@Entity
@Table(name = "inventario")
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Representa el inventario de un cliente, incluyendo la cantidad de productos y el nombre del producto.")
public class InventarioCliente {
    /*TODO_NUEVO*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del inventario, generado automáticamente.", example = "1")
    private Integer id;
    @Column
    @Schema(description = "Cantidad de productos en el inventario.", example = "10")    
    private Integer cantidadProd;

    @Column
    @Schema(description = "Nombre del producto en el inventario.", example = "Producto 1")
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false) 
    @Schema(description = "Cliente al que pertenece el inventario.", example = "1")
    private Cliente cliente;

}
