package com.multigacha.modificacion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.multigacha.modificacion.model.Empleado;
import com.multigacha.modificacion.model.Modificacion;
import com.multigacha.modificacion.service.ModificacionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/modificacion")
@Tag(name = "Modificacion", description = "Operacion Modificacion")
public class Controller {
    @Autowired
    private ModificacionService service;

    @GetMapping("/empleados")
    @Operation(summary = "Lista todos los empleados registrados.", description = "Retorna una lista de todos los empleados registrados en el sistema, incluyendo sus detalles como nombre, apellido e ID de contacto.")
    public ResponseEntity<List<Empleado>> listarEmpleados() {
        List<Empleado> lista = service.listarEmpleados();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(lista);
        }
    }

    @GetMapping("/modificaciones")
    @Operation(summary = "Lista todas las modificaciones registradas.", description = "Retorna una lista de todas las modificaciones realizadas en el sistema, incluyendo detalles como fecha, empleado responsable e ID del producto modificado.")
    public ResponseEntity<List<Modificacion>> listarModificaciones() {
        List<Modificacion> lista = service.listarModificaciones();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(lista);
        }
    }

    @GetMapping("/productos/modificaciones")
    @Operation(summary = "Lista las modificaciones por producto.", description = "Retorna una lista de modificaciones realizadas para un producto específico, incluyendo detalles como fecha y empleado responsable.")
    public ResponseEntity<List<Modificacion>> listarModificacionesPorProducto(Integer idProducto) {
        List<Modificacion> lista = service.mostrarModificacionesPorProducto(idProducto);
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(lista);
        }
    }

    @GetMapping("/empleados/modificaciones/{id}")
    @Operation(summary = "Lista las modificaciones por empleado.", description = "Retorna una lista de modificaciones realizadas por un empleado específico, identificado por su ID, incluyendo detalles como fecha y ID del producto modificado.")
    public ResponseEntity<List<Modificacion>> listarModificacionesPorEmpleado(@PathVariable Integer idEmpleado) {
        try {
            Empleado empleado = service.getEmpleado(idEmpleado);
            List<Modificacion> lista = service.listarModificacionesPorEmpledo(empleado);
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
