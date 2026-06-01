package com.multigacha.boleta.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name = "historial_boletas")
@Data
public class Boleta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numero_boleta")
    private Integer id;
    
    @Column(name = "id_cliente_comprador")
    private Integer idComprador;
    
    @Column(name = "id_cliente_vendedor")
    private Integer idVendedor; 
    
    @Column(name = "monto_total")
    private Double montoTotal;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_emision")
    private Date fecha;
    
    @Column(name = "detalle_transaccion", length = 255)
    private String detalle;
}
