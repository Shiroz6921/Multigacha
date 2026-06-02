package com.multigacha.torneo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.multigacha.torneo.model.torneo;
import com.multigacha.torneo.repository.TorneoRepository;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner initTorneoDataBase(TorneoRepository repo){
        return args->{
            if(repo.count()==0){
                torneo t1=repo.save(new torneo(null,"Yugimaxtournament",20));
                torneo t2=repo.save(new torneo(null,"BalatroChampionship",100));
                torneo t3=repo.save(new torneo(null,"Carlitos y sus 3 mazos flipantes",8));
            }
        };
    }

}
