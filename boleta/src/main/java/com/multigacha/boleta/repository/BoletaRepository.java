package com.multigacha.boleta.repository;

import com.multigacha.boleta.model.Boleta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoletaRepository extends JpaRepository<Boleta, Integer> {
}
