package com.multigacha.intercambio.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.multigacha.intercambio.model.ProductoCliente;

@Repository
public interface ProductoClienteRepo extends JpaRepository <ProductoCliente, Integer> {

    List<ProductoCliente> findByIdCliente(Integer idCliente);

    ProductoCliente findByIdProducto(Integer idProducto);

    Integer findIdClienteById(Integer id);

    Integer findIdProductoById(Integer id);


}
