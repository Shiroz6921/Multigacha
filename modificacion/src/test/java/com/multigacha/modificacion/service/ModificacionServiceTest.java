package com.multigacha.modificacion.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.multigacha.modificacion.client.ContactoClient;
import com.multigacha.modificacion.client.ProductoClient;
import com.multigacha.modificacion.dto.ProductoDTO;
import com.multigacha.modificacion.model.Empleado;
import com.multigacha.modificacion.model.Modificacion;
import com.multigacha.modificacion.repo.EmpleadoRepo;
import com.multigacha.modificacion.repo.ModificacionRepo;

@ExtendWith(MockitoExtension.class)
public class ModificacionServiceTest {

    @InjectMocks
    private ModificacionService modificacionService;

    @Mock
    private EmpleadoRepo empleadoRepo;

    @Mock
    private ModificacionRepo modificacionRepo;

    @Mock
    private ContactoClient contactoClient;

    @Mock
    private ProductoClient productoClient;

    private Empleado empleadoEjem;
    private Modificacion modificacionEjem;
    private ProductoDTO productoDTO;

    @BeforeEach
    public void setUp() {
        empleadoEjem = new Empleado();
        empleadoEjem.setId(1);
        empleadoEjem.setNombre("Juan");
        empleadoEjem.setApellido("Pérez");

        modificacionEjem = new Modificacion();
        modificacionEjem.setId(1);
        modificacionEjem.setFecha(new Date());
        modificacionEjem.setEmpleado(empleadoEjem);
        modificacionEjem.setIdProducto(1);

        productoDTO = new ProductoDTO();
        productoDTO.setId(1);
        productoDTO.setNombre("Pikachu ex");
    }

    @Test
    public void listarEmpleados_exitoso() {
        when(empleadoRepo.findAll()).thenReturn(List.of(empleadoEjem));

        List<Empleado> resultado = modificacionService.listarEmpleados();

        assertEquals(1, resultado.size());
        assertEquals(empleadoEjem.getId(), resultado.get(0).getId());
    }

    @Test
    public void listarEmpleados_vacio() {
        when(empleadoRepo.findAll()).thenReturn(List.of());

        List<Empleado> resultado = modificacionService.listarEmpleados();

        assertTrue(resultado.isEmpty());
    }

    @Test
    public void getEmpleado_exitoso() {
        when(empleadoRepo.findById(1)).thenReturn(Optional.of(empleadoEjem));

        Empleado resultado = modificacionService.getEmpleado(1);

        assertEquals(empleadoEjem.getId(), resultado.getId());
    }

    @Test
    public void getEmpleado_noEncontrado() {
        when(empleadoRepo.findById(99)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            modificacionService.getEmpleado(99);
        });

        assertEquals("Empleado no encontrado con ID: 99", exception.getMessage());
    }

    @Test
    public void crearEmpleado_exitoso() {
        when(empleadoRepo.save(any(Empleado.class))).thenReturn(empleadoEjem);

        Empleado resultado = modificacionService.crearEmpleado(empleadoEjem);

        assertEquals(empleadoEjem.getId(), resultado.getId());
        verify(empleadoRepo, times(1)).save(any(Empleado.class));
    }

    @Test
    public void crearEmpleado_errorAlGuardar() {
        when(empleadoRepo.save(any(Empleado.class)))
                .thenThrow(new RuntimeException("Error al guardar empleado"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            modificacionService.crearEmpleado(empleadoEjem);
        });

        assertEquals("Error al guardar empleado", exception.getMessage());
    }

    @Test
    public void listarModificaciones_exitoso() {
        when(modificacionRepo.findAll()).thenReturn(List.of(modificacionEjem));

        List<Modificacion> resultado = modificacionService.listarModificaciones();

        assertEquals(1, resultado.size());
    }

    @Test
    public void listarModificaciones_vacio() {
        when(modificacionRepo.findAll()).thenReturn(List.of());

        List<Modificacion> resultado = modificacionService.listarModificaciones();

        assertTrue(resultado.isEmpty());
    }

    @Test
    public void mostrarModificacionesPorProducto_exitoso() {
        when(modificacionRepo.findByIdProducto(1)).thenReturn(List.of(modificacionEjem));

        List<Modificacion> resultado = modificacionService.mostrarModificacionesPorProducto(1);

        assertEquals(1, resultado.size());
        assertEquals(modificacionEjem.getIdProducto(), resultado.get(0).getIdProducto());
    }

    @Test
    public void mostrarModificacionesPorProducto_vacio() {
        when(modificacionRepo.findByIdProducto(99)).thenReturn(List.of());

        List<Modificacion> resultado = modificacionService.mostrarModificacionesPorProducto(99);

        assertTrue(resultado.isEmpty());
    }

    @Test
    public void listarModificacionesPorEmpleado_exitoso() {
        when(empleadoRepo.findById(1)).thenReturn(Optional.of(empleadoEjem));
        when(modificacionRepo.findByEmpleado(empleadoEjem)).thenReturn(List.of(modificacionEjem));

        List<Modificacion> resultado = modificacionService.listarModificacionesPorEmpleado(1);

        assertEquals(1, resultado.size());
    }

    @Test
    public void listarModificacionesPorEmpleado_empleadoNoEncontrado() {
        when(empleadoRepo.findById(99)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            modificacionService.listarModificacionesPorEmpleado(99);
        });

        assertEquals("Empleado no encontrado con ID: 99", exception.getMessage());
    }

    @Test
    public void registrarModificacion_exitoso() {
        when(empleadoRepo.findById(1)).thenReturn(Optional.of(empleadoEjem));
        when(productoClient.buscarDTO(1)).thenReturn(productoDTO);
        when(modificacionRepo.save(any(Modificacion.class))).thenReturn(modificacionEjem);

        Modificacion resultado = modificacionService.registrarModificacion(1, 1);

        assertNotNull(resultado);
        assertEquals(modificacionEjem.getId(), resultado.getId());
        verify(modificacionRepo, times(1)).save(any(Modificacion.class));
    }

    @Test
    public void registrarModificacion_empleadoNoEncontrado() {
        when(empleadoRepo.findById(99)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            modificacionService.registrarModificacion(99, 1);
        });

        assertEquals("Empleado no encontrado con ID: 99", exception.getMessage());
    }

    @Test
    public void registrarModificacion_productoNoEncontrado() {
        when(empleadoRepo.findById(1)).thenReturn(Optional.of(empleadoEjem));
        when(productoClient.buscarDTO(99)).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            modificacionService.registrarModificacion(1, 99);
        });

        assertEquals("El producto con ID 99 no existe en el catálogo.", exception.getMessage());
    }
}