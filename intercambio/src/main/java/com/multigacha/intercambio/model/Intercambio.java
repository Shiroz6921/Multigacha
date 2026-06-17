package com.multigacha.intercambio.model;

import java.time.LocalDate;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "intercambio")
@Schema(description = "Representa un intercambio entre clientes, incluyendo la fecha del intercambio y los productos involucrados en el traspaso.")
public class Intercambio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del intercambio, generado automáticamente.", example = "1")
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "Fecha del intercambio.", example = "2023-10-10")
    private Date fecha;

    @OneToOne
    @JoinColumn(name = "producto_cliente_id", nullable = false)
    @Schema(description = "Producto involucrado en el intercambio.", example = "1")
    private ProductoCliente traspaso;

}
