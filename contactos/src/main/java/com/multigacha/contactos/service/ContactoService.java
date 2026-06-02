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
        return repo.findById(id).get();
    }
    public List<Contacto> listarContactos(){
        return repo.findAll();
    }
    public void addContacto(Contacto contacto){
        repo.save(contacto);
    }
    public void deleteContacto(Integer id){
        repo.deleteById(id);
    }
    public Contacto actualizarContacto(Contacto contactoNuevo){
        Contacto contactoViejo = repo.findById(contactoNuevo.getId()).get();
        if (contactoViejo.equals(null)){
            return null;
        }else{
            contactoViejo.setTelefono(contactoNuevo.getTelefono());
            contactoViejo.setDireccion(contactoNuevo.getDireccion());
            return contactoViejo;
        }
    }

}
