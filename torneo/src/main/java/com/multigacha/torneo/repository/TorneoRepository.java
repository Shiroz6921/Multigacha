package com.multigacha.torneo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.multigacha.torneo.model.torneo;
@Repository
public interface TorneoRepository extends JpaRepository<torneo, Integer>{

}
