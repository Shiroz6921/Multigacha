package com.multigacha.clientes.config;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.multigacha.clientes.model.Cliente;
import com.multigacha.clientes.repo.ClienteRepo;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner initClienteDataBase(ClienteRepo repo){
        return args ->{
            if(repo.count()==0){
      

            }
        };
    }

}
