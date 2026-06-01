package com.multigacha.compra.repository;

import com.multigacha.compra.model.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Integer> {
}
