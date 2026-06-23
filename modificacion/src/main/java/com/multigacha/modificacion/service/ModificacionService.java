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
    private EmpleadoRepo empleadoRepo; 
    
    @Autowired
    private ModificacionRepo modificacionRepo; 
    
    @Autowired
    private ContactoClient contactoClient;
    
    @Autowired
    private ProductoClient productoClient;


    public List<Empleado> listarEmpleados() {
        return empleadoRepo.findAll();
    }

    public Empleado getEmpleado(Integer id) {
        return empleadoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con ID: " + id));
    }

    public Empleado crearEmpleado(Empleado empleado) {
        if (contactoClient.buscarDTO(empleado.getIdContacto()) == null) {
            throw new RuntimeException("El contacto con ID " + empleado.getIdContacto() + " no existe.");
        }
        return empleadoRepo.save(empleado); 
    }



    public List<Modificacion> listarModificaciones() {
        return modificacionRepo.findAll();
    }

    public List<Modificacion> mostrarModificacionesPorProducto(Integer idProducto) {
        return modificacionRepo.findByIdProducto(idProducto);
    }

    public List<Modificacion> listarModificacionesPorEmpleado(Integer idEmpleado) {
        Empleado empleado = getEmpleado(idEmpleado); 
        return modificacionRepo.findByEmpleado(empleado);
    }

    public Modificacion registrarModificacion(Integer idEmpleado, Integer idProducto) {
        Empleado empleado = getEmpleado(idEmpleado);

        ProductoDTO productoDTO = productoClient.buscarDTO(idProducto);
        if (productoDTO == null) {
            throw new RuntimeException("El producto con ID " + idProducto + " no existe en el catálogo.");
        }

        Modificacion modificacion = new Modificacion();
        modificacion.setEmpleado(empleado);
        modificacion.setFecha(new Date());
        modificacion.setIdProducto(productoDTO.getId());

        return modificacionRepo.save(modificacion);
    }
}