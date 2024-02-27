package com.proyectofinal.bazar.service;

import com.proyectofinal.bazar.model.Producto;
import java.util.List;

public interface ProductoService {
    public void saveProducto(Producto producto);
    public Producto findProductoById(Long id);
    public void updateProducto(Producto producto, Long id) throws Exception;
    public void updateProducto(Producto producto);
    public void deleteProducto(Long id);
    public List<Producto> getProductos();
    public List<Producto> getProductosOutOfStock();
}
