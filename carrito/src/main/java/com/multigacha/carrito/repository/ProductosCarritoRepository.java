package com.multigacha.carrito.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.multigacha.carrito.model.Carrito;
import com.multigacha.carrito.model.ProductosCarritos;

public interface ProductosCarritoRepository extends JpaRepository<ProductosCarritos,Integer>{
        List<ProductosCarritos> findByCarrito(Carrito carrito);


}
