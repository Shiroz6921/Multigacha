package com.multigacha.catalogo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.multigacha.catalogo.model.Categoria;
import com.multigacha.catalogo.model.Producto;
import com.multigacha.catalogo.repository.CategoriaRepository;
import com.multigacha.catalogo.repository.ProductoRepository;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner initCatalogoDataBase(CategoriaRepository repo1, ProductoRepository repo2){
        return args->{
            if(repo1.count()==0 && repo2.count()==0){
                Categoria c1=repo1.save(new Categoria(null,"Espada y Escudo","Pokemon"));

                Producto p1=repo2.save(new Producto(null,"Pikachu",10000,c1,2));
                Producto p2=repo2.save(new Producto(null,"Charmander",15000,c1,4));
                Producto p3=repo2.save(new Producto(null,"Charizard",10000,c1,6));
            }
        };
    }
}
