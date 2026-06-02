package com.multigacha.modificacion.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multigacha.modificacion.client.ContactoClient;
import com.multigacha.modificacion.client.ProductoClient;

import com.multigacha.modificacion.dto.ProductoDTO;
import com.multigacha.modificacion.model.Empleado;
import com.multigacha.modificacion.model.Modificacion;
import com.multigacha.modificacion.repo.EmpleadoRepo;
import com.multigacha.modificacion.repo.ModificacionRepo;

@Service
public class ModificacionService {
    @Autowired
    private EmpleadoRepo repo1;
    @Autowired
    private ModificacionRepo repo2;
    @Autowired
    private ContactoClient contacto;
    @Autowired
    private ProductoClient producto;

    public List<Empleado> listarEmpleados() {
        return repo1.findAll();
    }

    public List<Modificacion> listarModificaciones() {
        return repo2.findAll();
    }

    public List<Modificacion> mostrarModificacionesPorProducto(Integer idProducto) {
        return repo2.findByIdProducto(idProducto);
    }

    public List<Modificacion> listarModificacionesPorEmpledo(Empleado empleado) {
        return repo2.findByEmpleado(empleado);
    }

    public Empleado mostrarEmpleadoPorModificacion(Integer id) {
        return repo2.findEmpleadoById(id);
    }

    public Empleado getEmpleado(Integer id) {
        return repo1.findById(id).get();
    }

    public void crearEmpleado(Empleado empleado) {
        if (contacto.buscarDTO(empleado.getIdContacto()) != null) {
            repo1.save(empleado);
        }
    }

    public Modificacion crearModificacion(Integer idEmpleado, Integer idProducto) {
        Empleado empleado = repo1.findById(idEmpleado).get();
        ProductoDTO productoDTO = producto.buscarDTO(idProducto);
        Modificacion modificacion = new Modificacion();
        modificacion.setEmpleado(empleado);
        modificacion.setFecha(new Date());
        modificacion.setIdProducto(productoDTO.getId());
        return modificacion;
    }

}
