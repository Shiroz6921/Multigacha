package com.multigacha.clientes.controller;

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

import com.multigacha.clientes.dto.ClienteDTO;
import com.multigacha.clientes.dto.InventarioDTO;
import com.multigacha.clientes.model.Cliente;
import com.multigacha.clientes.model.InventarioCliente;
import com.multigacha.clientes.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/clientes")
@Tag(name = "Clientes", description = "Operacion Clientes")
public class ClienteController {
    @Autowired
    private ClienteService servicio;

    @GetMapping
    @Operation(summary = "Lista todos los clientes registrados en el sistema.", description = "Retorna una lista de objetos Cliente con los detalles de cada cliente registrado. Si no hay clientes, retorna una respuesta sin contenido.")
    public ResponseEntity<List<Cliente>> listar() {
        if (servicio.mostrarClientes().isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(servicio.mostrarClientes());
        }
    }

    @PostMapping
    @Operation(summary = "Crea un nuevo cliente con su inventario asociado.", description = "Recibe un objeto ClienteDTO con los detalles del cliente a crear, incluyendo su inventario inicial. Retorna el cliente creado con su ID asignado y el inventario asociado.")
    public ResponseEntity<Cliente> crear (@RequestBody ClienteDTO dto) {
        return ResponseEntity.ok(servicio.clienteMasInventario(dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un cliente por su ID, junto con su inventario asociado.", description = "Recibe el ID del cliente a eliminar. Si el cliente existe, se elimina junto con su inventario y retorna una respuesta sin contenido. Si el cliente no existe, retorna una respuesta de no encontrado.")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            servicio.borrarClientePorId(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca un cliente por su ID.", description = "Recibe el ID del cliente a buscar. Si el cliente existe, retorna el objeto Cliente con sus detalles. Si el cliente no existe, retorna una respuesta de no encontrado.")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(servicio.mostrarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/actualizar/{id}")
    @Operation(summary = "Actualiza los datos de un cliente por su ID.", description = "Recibe el ID del cliente a actualizar y un objeto ClienteDTO con los nuevos datos. Si el cliente existe, retorna el objeto Cliente actualizado. Si el cliente no existe, retorna una respuesta de no encontrado.")
    public ResponseEntity<Cliente> actualizar(@PathVariable Integer id, @RequestBody ClienteDTO nuevo) {
        try {
            return ResponseEntity.ok(servicio.modificarCliente(id, nuevo));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/inventario/{idCliente}")
    @Operation(summary = "Agrega productos al inventario de un cliente.", description = "Recibe el ID del cliente y una lista de objetos InventarioDTO con los productos a agregar. Si el cliente existe, se agregan los productos al inventario y retorna una respuesta exitosa. Si el cliente no existe, retorna una respuesta de no encontrado.")
    public ResponseEntity<Void> agregarProductoAlInventario(@PathVariable Integer idCliente, @RequestBody List<InventarioDTO> inventarioDTO) {
        try {
            servicio.agregarProductos(idCliente, inventarioDTO);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/inventario/{idCliente}")
    @Operation(summary = "Obtiene el inventario de un cliente por su ID.", description = "Recibe el ID del cliente y retorna una lista de objetos InventarioCliente con los productos en su inventario. Si el cliente no existe, retorna una respuesta de no encontrado.")
    public ResponseEntity<List<InventarioCliente>> obtenerInventario(@PathVariable Integer idCliente) {
        try {
            return ResponseEntity.ok(servicio.obtenerInventarioPorCliente(idCliente));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
