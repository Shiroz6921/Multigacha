package com.multigacha.contactos.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.multigacha.contactos.model.Contacto;
import com.multigacha.contactos.repo.ContactoRepo;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner initContactoDataBase(ContactoRepo repo){
        return args->{
        if(repo.count()==0){
            Contacto c1= new Contacto(null,88888888,"avenida siempreviva 133");
            repo.save(new Contacto(null,45645645,"avenida siempreviva 411"));
            repo.save(new Contacto(null,81234567,"avenida siempreviva 103"));
            repo.save(new Contacto(null,84564566,"avenida siempreviva 113"));
            repo.save(new Contacto(null,45645666,"avenida siempreviva 421"));
            repo.save(new Contacto(null,81221556,"avenida siempreviva 143"));
            repo.save(new Contacto(null,83213118,"avenida siempreviva 233"));
            repo.save(new Contacto(null,78965466,"avenida siempreviva 011"));
            repo.save(new Contacto(null,85555555,"avenida siempreviva 803"));
            
            repo.save(c1);
        }
        };
    }
}
