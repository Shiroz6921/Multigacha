package com.multigacha.modificacion.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.multigacha.modificacion.model.Empleado;

@Repository
public interface EmpleadoRepo extends JpaRepository<Empleado, Integer> {
    String findNombreById(Integer id);

    String findApellidoById(Integer id);

    Integer findIdContactoById(Integer id);

    Empleado findByIdContacto(Integer id);

    List<Empleado> findByNombre(String nombre);

    List<Empleado> findByApellido(String apellido);
}
