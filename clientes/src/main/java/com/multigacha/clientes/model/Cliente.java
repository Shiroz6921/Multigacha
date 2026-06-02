package com.multigacha.clientes.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, length = 13, nullable = false)
    private String run;

    @Column(length = 40, nullable = false)
    private String nombre;

    @Column(length = 40, nullable = false)
    private String apellido;
    /*ORIGINAL
    @Column(nullable = false) 
    original = private Date fechNac;*/

    
    /*agregar esto para añadir fechas = @JsonFormat(pattern = "dd/MM/yyyy", timezone = "GMT-4")
    alternativas = 1.-@CreationTimestamp
                      private LocalDate fechNac;
                    
                    2.-@CreationTimestamp
                       @Temporal(TemporalType.DATE) .DATE para guadar dd/mm/yyyy
                       @Column(name = "fech_nac", nullable = false)
                       private Date fechNac;*/
    //NUEVO
    @CreationTimestamp
    @Column(nullable = false) 
    private LocalDate fechNac;

    /*private Integer idContacto
    le pide un id al cliente XD*/

    //NUEVO
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InventarioCliente> inventarios = new ArrayList<>();
}
