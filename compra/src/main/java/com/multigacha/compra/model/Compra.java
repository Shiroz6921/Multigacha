package com.multigacha.compra.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name = "transacciones_compra")
@Data
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaccion")
    private Integer id;
    
    @Column(name = "id_comprador_final")
    private Integer idComprador;
    
    @Column(name = "id_publicacion_venta")
    private Integer idVenta; 
    
    @Column(name = "monto_pagado")
    private Double totalPagado;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_transaccion")
    private Date fechaCompra;
}