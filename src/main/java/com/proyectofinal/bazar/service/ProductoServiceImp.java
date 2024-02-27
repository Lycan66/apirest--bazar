package com.proyectofinal.bazar.service;

import com.proyectofinal.bazar.model.Producto;
import com.proyectofinal.bazar.repository.ProductoRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoServiceImp implements ProductoService{

    @Autowired
    ProductoRepository productoRepo;
    
    @Override
    public void saveProducto(Producto producto) {
        productoRepo.save(producto);
    }

    @Override
    public Producto findProductoById(Long id) throws NullPointerException{
        Producto producto = productoRepo.findById(id).orElse(null);
        
        if (producto == null){
            throw new NullPointerException("Producto con ID " + id + " no encontrado");
        }
        return producto;
    }

    @Override
    public void updateProducto(Producto upProducto, Long id) throws Exception {
        if(productoRepo.existsById(id)){
            this.saveProducto(upProducto);
        }
        else{
            throw new Exception ("No se pudo realizar esta acción, por favor revise el ID");
        }
    }

    @Override
    public void deleteProducto(Long id) {
        productoRepo.deleteById(id);
    }

    @Override
    public List<Producto> getProductos() {
        return productoRepo.findAll();
    }

    @Override
    public void updateProducto(Producto producto) {
        this.saveProducto(producto);
    }
    
    @Override
    public List<Producto> getProductosOutOfStock(){
        List<Producto> productos = this.getProductos();
        List<Producto> productosFaltaStock = new ArrayList<>();
        
        for (Producto producto : productos){
            if(producto.isOutOfStock()){
                productosFaltaStock.add(producto);
            }
        }
        
        return productosFaltaStock;
    }
    
}
