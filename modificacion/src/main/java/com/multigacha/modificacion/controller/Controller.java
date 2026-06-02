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

@RestController
@RequestMapping("api/v1/modificacion")
public class Controller {
    @Autowired
    private ModificacionService service;

    @GetMapping("/empleados")
    public ResponseEntity<List<Empleado>> listarEmpleados() {
        List<Empleado> lista = service.listarEmpleados();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(lista);
        }
    }

    @GetMapping("/modificaciones")
    public ResponseEntity<List<Modificacion>> listarModificaciones() {
        List<Modificacion> lista = service.listarModificaciones();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(lista);
        }
    }

    @GetMapping("/productos/modificaciones")
    public ResponseEntity<List<Modificacion>> listarModificacionesPorProducto(Integer idProducto) {
        List<Modificacion> lista = service.mostrarModificacionesPorProducto(idProducto);
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(lista);
        }
    }

    @GetMapping("/empleados/modificaciones/{id}")
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
