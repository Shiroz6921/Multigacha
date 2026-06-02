package com.multigacha.torneo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.multigacha.torneo.model.inscripciones;
@Repository
public interface InscripcionesRepository extends JpaRepository<inscripciones, Integer>{
    boolean existsByClienteIdAndTorneoId(Integer clienteId, Integer torneoId) ;

}
