package com.multigacha.carrito.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.multigacha.carrito.model.Carrito;
@Repository
public interface CarritoRepository extends JpaRepository <Carrito,Integer>{

}
