package com.multigacha.clientes.config;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.multigacha.clientes.model.Cliente;
import com.multigacha.clientes.model.InventarioCliente;
import com.multigacha.clientes.repo.ClienteRepo;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner initClienteDataBase(ClienteRepo repo){
        return args ->{
           if (repo.count() == 0) {
    // 1. Crear el cliente
    Cliente cliente = new Cliente();
    cliente.setRun("22123344-4");
    cliente.setNombre("big");
    cliente.setApellido("t");
    cliente.setFechNac(LocalDate.now());
    
    // 2. Crear los productos para el inventario
    List<InventarioCliente> listaInventario = new ArrayList<>();
    
    InventarioCliente item1 = new InventarioCliente();
    item1.setNombre("Producto 1");
    item1.setCantidadProd(10);
    item1.setCliente(cliente); // IMPORTANTE: Vincular el hijo al padre
    
    InventarioCliente item2 = new InventarioCliente();
    item2.setNombre("Producto 2");
    item2.setCantidadProd(5);
    item2.setCliente(cliente); // IMPORTANTE: Vincular el hijo al padre
    
    listaInventario.add(item1);
    listaInventario.add(item2);
    
    // 3. Asignar la lista al cliente
    cliente.setInventarios(listaInventario);
    
    // 4. Guardar (el cascade hará que también se guarden los items del inventario)
    repo.save(cliente);
                }
            };
        }
    }

