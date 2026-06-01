package com.multigacha.venta.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "publicaciones_venta")
@Data
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_publicacion")
    private Integer id;
    
    @Column(name = "id_vendedor", nullable = false)
    private Integer idVendedor; 
    
    @Column(name = "id_producto_carta", nullable = false)
    private Integer idProducto; 
    
    @Column(name = "precio_venta", nullable = false)
    private Double precio; 
    
    @Column(name = "estado_publicacion", length = 20)
    private String estado;
}