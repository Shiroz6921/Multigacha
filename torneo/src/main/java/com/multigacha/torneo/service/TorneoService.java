package com.multigacha.torneo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multigacha.torneo.client.ClientesClient;
import com.multigacha.torneo.dto.ClienteDTO;
import com.multigacha.torneo.model.inscripciones;
import com.multigacha.torneo.model.torneo;
import com.multigacha.torneo.repository.InscripcionesRepository;
import com.multigacha.torneo.repository.TorneoRepository;
@Service
public class TorneoService {

    @Autowired
    private InscripcionesRepository repo1;
    
    @Autowired
    private TorneoRepository repo2;
    @Autowired
    private ClientesClient cliente;


    public torneo crearTorneo (torneo torneo){
        return repo2.save(torneo);
    }

    public inscripciones agregarGente(Integer torneoId, ClienteDTO clienteId){
        ClienteDTO clienteDto = cliente.buscarPorId(clienteId.getId());
        torneo torneo = repo2.findById(torneoId)
                 .orElseThrow(() -> new RuntimeException("Torneo no encontrado"));

        boolean Inscrito = repo1.existsByClienteIdAndTorneoId(clienteId.getId(), torneoId);
        if (Inscrito) {
            throw new RuntimeException("Esta persona ya se encuentra inscrita en este torneo.");
        }
        inscripciones inscripciones = new inscripciones();
        inscripciones.setClienteId(clienteDto.getId());
        inscripciones.setNombre(clienteDto.getNombre());
        inscripciones.setTorneo(torneo);
        return repo1.save(inscripciones);
    }

    public List<inscripciones> obtenerTodo(){
        return repo1.findAll();
    }
}
