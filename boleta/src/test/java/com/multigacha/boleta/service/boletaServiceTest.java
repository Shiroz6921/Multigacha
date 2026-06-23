package com.multigacha.boleta.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.multigacha.boleta.model.Boleta;
import com.multigacha.boleta.repository.BoletaRepository;

@ExtendWith(MockitoExtension.class)

public class boletaServiceTest {

    @InjectMocks
    private BoletaService boletaService;

    @Mock
    private BoletaRepository boletaRepository;

    private Boleta boleta;

    @BeforeEach
    public void setUp() {
        boleta = new Boleta();
        boleta.setId(1);
        boleta.setIdComprador(2);
        boleta.setIdComprador(null);
        boleta.setDetalle("Compra de productos");
        boleta.setFecha(new java.util.Date());
        boleta.setMontoTotal(1000.00);
    }   

    @Test
    public void generarBoleta() {

        when(boletaRepository.save(any(Boleta.class)))
                .thenReturn(boleta);
        //act
        Boleta resultado = boletaService.generarBoleta(boleta);

        //assert
        assertEquals(boleta.getId(), resultado.getId()); 
        assertEquals(boleta.getMontoTotal(), resultado.getMontoTotal());
    }
    
    @Test
    public void errorAlGuardarBoleta() {

        when(boletaRepository.save(any(Boleta.class)))
                .thenThrow(new RuntimeException("Error al guardar la boleta"));
        //act & assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            boletaService.generarBoleta(boleta);
        });

        assertEquals("Error al guardar la boleta", exception.getMessage());
    }

    @Test
    public void obtenerBoletaPorId() {

        when(boletaRepository.findById(1))
                .thenReturn(Optional.of(boleta));
        //act
        Boleta resultado = boletaService.obtenerBoletaPorId(1);
        //assert
        assertEquals(boleta.getId(), resultado.getId()); 
        assertEquals(boleta.getMontoTotal(), resultado.getMontoTotal());
    }

    @Test
    public void obtenerBoletaPorIdNoEncontrada() {

        when(boletaRepository.findById(1))
                .thenReturn(Optional.empty());
        //act & assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            boletaService.obtenerBoletaPorId(1);
        });

        assertEquals("Boleta no encontrada", exception.getMessage());
    }

    @Test
    public void obtenerTodasLasBoletas() {

        List<Boleta> boletas = new ArrayList<>();
        boletas.add(boleta);

        when(boletaRepository.findAll()).thenReturn(boletas);

        List<Boleta> resultado = boletaService.obtenerTodasLasBoletas();

        assertEquals(1, resultado.size());
        assertEquals(boleta.getId(), resultado.get(0).getId());
    }
    
    @Test
    public void obtenerTodasLasBoletas_vacia() {
        when(boletaRepository.findAll()).thenReturn(new ArrayList<>());

        List<Boleta> resultado = boletaService.obtenerTodasLasBoletas();

        assertEquals(0, resultado.size());
    }
}   