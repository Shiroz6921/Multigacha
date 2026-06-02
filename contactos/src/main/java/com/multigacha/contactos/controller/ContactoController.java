package com.multigacha.contactos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.multigacha.contactos.model.Contacto;
import com.multigacha.contactos.service.ContactoService;

@RestController
@RequestMapping("api/v1/contactos")
public class ContactoController {
    @Autowired
    private ContactoService service;

    @GetMapping("/lista")
    public ResponseEntity<List<Contacto>> listar(){
        List<Contacto> lista = service.listarContactos();
        if(lista.equals(null)){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(lista);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contacto> buscarContacto(@PathVariable Integer id){
        try {
            Contacto contacto = service.getContacto(id);
            return ResponseEntity.ok(contacto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Void> agregarContacto(@RequestBody Contacto contacto){
        try {
            service.addContacto(contacto);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/updt")
    public ResponseEntity<Contacto> actualizarContacto(@RequestBody Contacto contactoNuevo){
        Contacto contacto = service.actualizarContacto(contactoNuevo);
        if(contacto.equals(null)){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(contacto);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarContacto(@PathVariable Integer id){
        try {
            service.deleteContacto(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
