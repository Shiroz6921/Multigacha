package com.multigacha.clientes.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multigacha.clientes.client.ContactoClient;
import com.multigacha.clientes.dto.ClienteDTO;
import com.multigacha.clientes.dto.ContactoDTO;
import com.multigacha.clientes.dto.InventarioDTO;
import com.multigacha.clientes.model.Cliente;
import com.multigacha.clientes.model.InventarioCliente;
import com.multigacha.clientes.repo.ClienteRepo;
import com.multigacha.clientes.repo.InventarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ClienteService {
    @Autowired
    private ClienteRepo repo;

    @Autowired
    private ContactoClient contacto;

    @Autowired
    private InventarioRepository repoInv;

    public List<Cliente> mostrarClientes() {
        return repo.findAll();
    }

    public Cliente mostrarPorId(Integer id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    //NUEVO
    public Cliente clienteMasInventario (ClienteDTO dto){
        Cliente nuevo = new Cliente();
        nuevo.setRun(dto.getRun());
        nuevo.setNombre(dto.getNombre());
        nuevo.setApellido(dto.getApellido());
        //fecha e inventario se crea solo
        return repo.save(nuevo);

        /*CONTACTO DEBERIA CREARSE JUNTO CON EL CLIENTE NO POR SEPARADO    
        EJEMPLO 
        ContactoDTO contactoDTO = new ContactoDTO();
        contactoDTO.setTelefono(dto.getContacto().getTelefono());
        contactoDTO.setDireccion(dto.getContacto().getDireccion());

        contacto.crearContacto(nuevo.getId(),contactoDTO);*/
    }

        /* 
        public Cliente agregarCliente(Cliente cliente) {
            //no se puede CREAR ya que necesitas un dato date a no ser que pongas "1995-05-22T14:30:00.000+00:00"  
            return repo.save(cliente);
        }
        */

    public void borrarClientePorId(Integer id) {
        repo.deleteById(id);
    }

    public List<InventarioCliente> inventarioPorIdCliente (Integer clienteId){
        return repoInv.findByClienteId(clienteId);
    }
    
    public Cliente modificarCliente(Integer idCliente, ClienteDTO nuevo) {
        Cliente viejo = repo.findById(idCliente).get();
        ContactoDTO contactoDTO = contacto.buscarContacto(idCliente);
        viejo.setNombre(nuevo.getNombre());
        viejo.setApellido(nuevo.getApellido());
        viejo.setFechNac(nuevo.getFechNac());

        contactoDTO.setDireccion(nuevo.getContacto().getDireccion());
        contactoDTO.setTelefono(nuevo.getContacto().getTelefono());
        //falta el PUT de contacto, no se guarda en ningun lugar la modificacion de contacto 
        
        /*no se guardaba la modificacion
        linea original = return viejo;*/

        //NUEVO
        return repo.save(viejo);
    }


    public void agregarProductos(Integer clienteId, List<InventarioDTO> listaDto){
        Cliente cliente = repo.findById(clienteId)
        .orElseThrow(() -> new RuntimeException("Cliente no registrado "));

        List<InventarioCliente> guardarProd = new ArrayList<>();
        //Recorre la lista que envia compra 
        for (InventarioDTO dto : listaDto) {
            InventarioCliente nuevoItem = new InventarioCliente();
            nuevoItem.setNombre(dto.getNombre());
            nuevoItem.setCantidadProd(dto.getCantidadProd());
            nuevoItem.setCliente(cliente);
            guardarProd.add(nuevoItem);
        }
        repoInv.saveAll(guardarProd);
    }
}