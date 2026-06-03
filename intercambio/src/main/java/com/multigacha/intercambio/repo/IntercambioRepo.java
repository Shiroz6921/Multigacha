package com.multigacha.intercambio.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.multigacha.intercambio.model.Intercambio;

@Repository
public interface IntercambioRepo extends JpaRepository<Intercambio, Integer> {

}