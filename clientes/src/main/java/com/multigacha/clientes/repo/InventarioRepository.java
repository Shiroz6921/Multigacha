package com.multigacha.clientes.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.multigacha.clientes.model.InventarioCliente;

public interface InventarioRepository extends JpaRepository <InventarioCliente,Integer>{
    List<InventarioCliente> findByClienteId(Integer id);
}
