package com.multigacha.modificacion.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.multigacha.modificacion.model.Empleado;
import com.multigacha.modificacion.repo.EmpleadoRepo;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner initModificacionDataBase(EmpleadoRepo repo){
        return args->{
            if(repo.count()==0){
        }
    };
    
}
}
