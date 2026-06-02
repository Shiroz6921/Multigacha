package com.multigacha.catalogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.multigacha.catalogo.model.Producto;
@Repository
public interface ProductoRepository extends JpaRepository <Producto,Integer>{

}
