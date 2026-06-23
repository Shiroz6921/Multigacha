package com.multigacha.modificacion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.multigacha.modificacion.model.Empleado;
import com.multigacha.modificacion.model.Modificacion;
import com.multigacha.modificacion.service.ModificacionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/modificaciones") 
@Tag(name = "Modificacion", description = "Operaciones para gestionar Empleados y su registro de Modificaciones")
public class Controller {

    @Autowired
    private ModificacionService service;

    @GetMapping("/empleados")
    @Operation(summary = "Lista todos los empleados registrados.")
    public ResponseEntity<List<Empleado>> listarEmpleados() {
        List<Empleado> lista = service.listarEmpleados();
        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
    }

    @PostMapping("/empleados")
    @Operation(summary = "Crea un nuevo empleado validando su ID de contacto.")
    public ResponseEntity<?> crearEmpleado(@RequestBody Empleado empleado) {
        try {
            Empleado nuevoEmpleado = service.crearEmpleado(empleado);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEmpleado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    @Operation(summary = "Lista todas las modificaciones registradas en el sistema.")
    public ResponseEntity<List<Modificacion>> listarModificaciones() {
        List<Modificacion> lista = service.listarModificaciones();
        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
    }

    @GetMapping("/producto/{idProducto}")
    @Operation(summary = "Lista las modificaciones para un producto específico.")
    public ResponseEntity<List<Modificacion>> listarModificacionesPorProducto(@PathVariable Integer idProducto) {
        List<Modificacion> lista = service.mostrarModificacionesPorProducto(idProducto);
        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
    }

    @GetMapping("/empleado/{idEmpleado}")
    @Operation(summary = "Lista las modificaciones realizadas por un empleado específico.")
    public ResponseEntity<?> listarModificacionesPorEmpleado(@PathVariable Integer idEmpleado) {
        try {
            List<Modificacion> lista = service.listarModificacionesPorEmpleado(idEmpleado);
            return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    @Operation(summary = "Registra que un empleado modificó un producto.")
    public ResponseEntity<?> registrarModificacion(
            @RequestParam Integer idEmpleado, 
            @RequestParam Integer idProducto) {
        try {
            Modificacion nuevaModificacion = service.registrarModificacion(idEmpleado, idProducto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaModificacion);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}