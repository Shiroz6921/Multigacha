package com.multigacha.boleta.service;

import com.multigacha.boleta.model.Boleta;
import com.multigacha.boleta.repository.BoletaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class BoletaService {
    @Autowired
    private BoletaRepository repo;

    public Boleta generarBoleta(Boleta boleta) {
        boleta.setFecha(new Date());
        return repo.save(boleta);
    }
}