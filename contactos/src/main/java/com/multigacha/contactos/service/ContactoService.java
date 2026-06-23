package com.multigacha.contactos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multigacha.contactos.model.Contacto;
import com.multigacha.contactos.repo.ContactoRepo;

@Service
public class ContactoService {
    @Autowired
    private ContactoRepo repo;

    public Contacto getContacto(Integer id){
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Contacto no encontrado"));
    }

    public List<Contacto> listarContactos(){
        return repo.findAll();
    }

    public Contacto addContacto(Contacto contacto){
        return repo.save(contacto);
    }

    public void deleteContacto(Integer id){
       if(!repo.existsById(id)){
        throw new RuntimeException("Contacto no encontrado");
    }
        repo.deleteById(id);
    }

    public Contacto actualizarContacto(Contacto contactoNuevo){
        Contacto contactoViejo = repo.findById(contactoNuevo.getId())
            .orElseThrow(() -> new RuntimeException("Contacto no encontrado"));
            contactoViejo.setTelefono(contactoNuevo.getTelefono());
            contactoViejo.setDireccion(contactoNuevo.getDireccion());
            return repo.save(contactoViejo);
    }
}


