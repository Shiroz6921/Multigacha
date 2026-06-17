package com.multigacha.venta.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "publicaciones_venta")
@Data
@Schema(description = "Representa una publicación de venta de un producto, incluyendo detalles como el vendedor, el producto, el precio y el estado de la publicación.")    
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_publicacion")
    @Schema(description = "ID único de la publicación de venta, generado automáticamente.", example = "1")
    private Integer id;
    
    @Column(name = "id_vendedor", nullable = false)
    @Schema(description = "ID del vendedor que publicó el producto.", example = "1")
    private Integer idVendedor; 
    
    @Column(name = "id_producto_carta", nullable = false)
    @Schema(description = "ID del producto que se está vendiendo.", example = "1")
    private Integer idProducto; 
    
    @Column(name = "precio_venta", nullable = false)
    @Schema(description = "Precio de venta del producto.", example = "19.99")
    private Double precio; 
    
    @Column(name = "estado_publicacion", length = 20)
    @Schema(description = "Estado de la publicación de venta.", example = "Activa")
    private String estado;
}