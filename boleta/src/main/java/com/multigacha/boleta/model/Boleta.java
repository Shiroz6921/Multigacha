package com.multigacha.boleta.model;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name = "historial_boletas")
@Data
@Schema(description = "Representa el historial de transacciones de compra-venta entre clientes, incluyendo detalles como monto total, fecha y descripción de la transacción.")
public class Boleta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numero_boleta")
    @Schema(description = "Número único de la boleta, generado automáticamente.",example = ("1"))
    private Integer id;
    
    @Column(name = "id_cliente_comprador")
    @Schema(description = "ID del cliente comprador involucrado en la transacción.",example = ("2"))
    private Integer idComprador;
    
    @Column(name = "id_cliente_vendedor")
    @Schema(description = "ID del cliente vendedor involucrado en la transacción.",example = ("3"))
    private Integer idVendedor; 
    
    @Column(name = "monto_total")
    @Schema(description = "Monto total de la transacción.",example = ("100.00"))
    private Double montoTotal;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_emision")
    @Schema(description = "Fecha y hora de emisión de la boleta.",example = ("2023-01-01T10:00:00"))
    private Date fecha;
    
    @Column(name = "detalle_transaccion", length = 255)
    @Schema(description = "Detalle de la transacción.",example = ("Compra de productos"))
    private String detalle;
}
