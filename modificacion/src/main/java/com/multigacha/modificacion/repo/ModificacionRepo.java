package com.multigacha.modificacion.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.multigacha.modificacion.model.Empleado;
import com.multigacha.modificacion.model.Modificacion;

@Repository
public interface ModificacionRepo extends JpaRepository<Modificacion, Integer> {

}
