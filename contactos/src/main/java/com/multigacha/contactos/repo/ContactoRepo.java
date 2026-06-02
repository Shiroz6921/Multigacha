package com.multigacha.contactos.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.multigacha.contactos.model.Contacto;
@Repository
public interface ContactoRepo extends JpaRepository<Contacto,Integer>{

}
