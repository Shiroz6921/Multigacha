package com.multigacha.catalogo.service;

import com.multigacha.catalogo.repository.CategoriaRepository;
import com.multigacha.catalogo.repository.ProductoRepository;
import com.multigacha.catalogo.dto.ProductoDTO;
import com.multigacha.catalogo.model.Categoria;
import com.multigacha.catalogo.model.Producto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CatalogoService {

    @Autowired
    private CategoriaRepository repo1;

    @Autowired
    private ProductoRepository repo2;
    
    public Categoria guardarExpansion(Categoria categoria) {
    categoria.setId(null);

    if (categoria.getProductos() != null) {
        categoria.getProductos().forEach(p -> {p.setCategoria(categoria);
            p.setId(null); 
        });
    }
    return repo1.save(categoria);
    }
    
    public Producto agregarCarta(ProductoDTO dto) {
        Categoria categoria = repo1.findById(dto.getCategoriaId())
            .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        Producto nuevo = new Producto();
        nuevo.setNombre(dto.getNombre());
        nuevo.setPrecio(dto.getPrecio());
        nuevo.setStock(dto.getStock());
        nuevo.setCategoria(categoria);

        return repo2.save(nuevo);   
    }

    public List<Categoria> obtenerTodo() {
        return repo1.findAll();
    }
    
    public Producto buscarPorId(Integer id) {
        return repo2.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    public Producto bajarStock(Integer id, Integer cantidad){
        Producto a = buscarPorId(id);
        if (a.getStock() < cantidad) {
            throw new RuntimeException("no hay suficientes cartas");
        }
        a.setStock(a.getStock()-cantidad);
        return repo2.save(a);
    }
}