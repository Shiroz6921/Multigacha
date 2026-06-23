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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/contactos")
@Tag(name = "Contactos", description = "Operacion Contactos")
public class ContactoController {
    @Autowired
    private ContactoService service;

    @GetMapping("/lista")
    @Operation(summary = "Lista todos los contactos registrados en el sistema.", description = "Retorna una lista de objetos Contacto con los detalles de cada contacto registrado. Si no hay contactos, retorna una respuesta sin contenido.")
    public ResponseEntity<List<Contacto>> listar(){
        List<Contacto> lista = service.listarContactos();
        if(lista.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(lista);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca un contacto por su ID.", description = "Retorna los detalles de un contacto específico según su ID. Si no se encuentra, retorna una respuesta de no encontrado.")
    public ResponseEntity<Contacto> buscarContacto(@PathVariable Integer id){
        try {
            Contacto contacto = service.getContacto(id);
            return ResponseEntity.ok(contacto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    @Operation(summary = "Agrega un nuevo contacto.", description = "Registra un nuevo contacto en el sistema. Retorna una respuesta de éxito si el contacto es agregado correctamente.")
    public ResponseEntity<Void> agregarContacto(@RequestBody Contacto contacto){
        try {
            service.addContacto(contacto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/updt")
    @Operation(summary = "Actualiza la información de un contacto existente.", description = "Modifica los datos de un contacto específico según su ID. Retorna el contacto actualizado si la operación es exitosa.")
    public ResponseEntity<Contacto> actualizarContacto(@RequestBody Contacto contactoNuevo){
        try {
            Contacto contacto = service.actualizarContacto(contactoNuevo);
            return ResponseEntity.ok(contacto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    @Operation(summary = "Elimina un contacto existente.", description = "Elimina un contacto específico según su ID. Retorna una respuesta de éxito si la operación es exitosa.")
    public ResponseEntity<Void> eliminarContacto(@PathVariable Integer id){
        try {
            service.deleteContacto(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
