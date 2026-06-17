package com.multigacha.compra.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "transacciones_compra")
@Data
@Schema(description = "Representa una transacción de compra realizada por un cliente, incluyendo el ID del comprador, el ID de la venta, el monto pagado y la fecha de la transacción.")
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaccion")
    @Schema(description = "ID único de la transacción, generado automáticamente.", example = ("1"))
    private Integer id;
    
    @Column(name = "id_comprador_final")
    @Schema(description = "ID del comprador final.", example = ("1"))
    private Integer idComprador;
    
    @Column(name = "id_publicacion_venta")
    @Schema(description = "ID de la publicación de venta.", example = ("1"))
    private Integer idVenta; 
    
    @Column(name = "monto_pagado")
    @Schema(description = "Monto total pagado por la compra.", example = ("100.00"))
    private Double totalPagado;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_transaccion")
    @Schema(description = "Fecha y hora de la transacción.", example = ("2023-10-01T10:00:00Z"))
    private Date fechaCompra;
}