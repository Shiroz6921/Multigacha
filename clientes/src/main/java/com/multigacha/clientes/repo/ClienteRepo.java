package com.multigacha.clientes.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.multigacha.clientes.model.Cliente;

@Repository
public interface ClienteRepo extends JpaRepository<Cliente, Integer> {
    //METODOS INNECESARIOS Y QUE NO SE USAN 

    /*Cliente findByRun(String run);

    //List<Cliente> findByNombre(String nombre);

    //List<Cliente> findByApellido(String apellido);

    //List<Cliente> findByFechNac(Date fechNac);

    //String findRunById(Integer id);

    //String findNombreById(Integer id);

    //String findApellidoById(Integer id);

    //Date findFechNacById(Integer id);

    //String findNombreByRun(String run);

    //String findApellidoByRun(String run);

    //Date findFechNacByRun(String run);*/
}
