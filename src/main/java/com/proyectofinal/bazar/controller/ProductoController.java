package com.proyectofinal.bazar.controller;

import com.proyectofinal.bazar.model.Producto;
import com.proyectofinal.bazar.service.ProductoService;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/productos")
public class ProductoController {
    
    @Autowired
    ProductoService productoService;
    
    @PostMapping("/crear")
    public ResponseEntity saveCliente(@RequestBody Producto producto) {
        productoService.saveProducto(producto);
        return new ResponseEntity(HttpStatus.CREATED);
    }
    
    @GetMapping("/traer-todos")
    public List<Producto> getProductos() {
        List<Producto> listaProductos = productoService.getProductos();
        return listaProductos;
    }
    
    @GetMapping("/{id_producto}")
    public ResponseEntity getProducto(@PathVariable Long id_producto) {
        Producto producto;
        try {
            producto = productoService.findProductoById(id_producto);
        } catch (NullPointerException e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(producto,HttpStatus.OK);
    }
    
    @DeleteMapping("/eliminar/{id_producto}")
    public void deleteProducto(@PathVariable Long id_producto){
        productoService.deleteProducto(id_producto);
    }
    
    @PutMapping("/editar/{id_producto}")
    public ResponseEntity updateProducto(@PathVariable Long id_producto, 
                                        @RequestBody Producto producto) {
        
        try{
            productoService.updateProducto(producto, id_producto);
        }catch(Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        
        return new ResponseEntity(producto, HttpStatus.OK);
    }
    
    @GetMapping("/falta_stock")
    public List<Producto> getProductosOutOfStock() {
        List<Producto> productos = productoService.getProductosOutOfStock();
        return productos;
    }
}
