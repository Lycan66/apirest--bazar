package com.proyectofinal.bazar.service;

import com.proyectofinal.bazar.model.Producto;
import com.proyectofinal.bazar.model.Venta;
import com.proyectofinal.bazar.repository.VentaRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VentaServiceImp implements VentaService {

    private static final Logger LOG = LoggerFactory.getLogger(VentaServiceImp.class);

    @Autowired
    VentaRepository ventaRepo;
    @Autowired
    ProductoService productoService;

    @Override
    public void saveVenta(Venta venta) throws Exception {
        Map<Long, Double> cantidadVendidaPorProducto = mantenerCantidadVendidaPorProducto(venta.getListaProductos());
        List<Producto> productosActualizados = updateProductosVendidos(venta);
        venta.setListaProductos(productosActualizados);
        venta.calcularValorTotal(cantidadVendidaPorProducto);
        ventaRepo.save(venta);
    }

    private Map<Long, Double> mantenerCantidadVendidaPorProducto(List<Producto> productos){
        Map<Long, Double> cantidadVendidaProducto = new HashMap<>();
        for (Producto pro : productos){
            cantidadVendidaProducto.put(pro.getCodigo(), pro.getCantidadVendida());
        }
        return cantidadVendidaProducto;
    }
    @Override
    public Venta findVentaById(Long id) {
        Venta venta = ventaRepo.findById(id).orElse(null);

        if (venta == null) {
            throw new NullPointerException("Venta " + id + ": no encontrada");
        }
        return venta;
    }

    @Override
    public void deleteVenta(Long id) {
        ventaRepo.deleteById(id);
    }

    @Override
    public List<Venta> getVentas() {
        return ventaRepo.findAll();
    }

    private List<Producto> updateProductosVendidos(Venta venta) throws Exception {
        List<Producto> listaProductosVendidos = venta.getListaProductos();
        List<Producto> listaProductosVendidosActualizados = new ArrayList<>();

        for (Producto producto : listaProductosVendidos) {
            Producto producto_bd = productoService.findProductoById(producto.getCodigo());
            Double cantidadVendidaDelProducto = producto_bd.getCantidadVendida();
            
            producto_bd.setCantidadVendida(cantidadVendidaDelProducto += producto.getCantidadVendida());
            producto_bd.descontarStock(producto.getCantidadVendida());
            listaProductosVendidosActualizados.add(producto_bd);
        }

        for (Producto producto : listaProductosVendidosActualizados) {
            productoService.updateProducto(producto);
        }

        return listaProductosVendidosActualizados;
    }

    @Override
    public List<Producto> getProductosByVenta(Long id_venta) throws NullPointerException {
        Venta venta = this.findVentaById(id_venta);
        List<Producto> productos = venta.getListaProductos();
        return productos;
    }

    @Override
    public Double getMontoTotalDeVentasPorFecha(LocalDate fecha) throws Exception{
        List<Venta> ventas = getVentasByFecha(fecha);
        Double montoTotal = 0.0;
        if (ventas.isEmpty()){
            throw new Exception ("No hay ventas");
        }
        
        for (Venta venta : ventas) {
            montoTotal += venta.getTotal();
        }
        return montoTotal;
    }

    @Override
    public int getNumeroDeVentasPorFecha(LocalDate fecha) throws Exception {
        List<Venta> ventas = getVentasByFecha(fecha);
        if (ventas.isEmpty()){
            throw new Exception ("No hay ventas");
        }
        return ventas.size();
    }

    private List<Venta> getVentasByFecha(LocalDate fecha) throws Exception{
        LocalDate hoy = LocalDate.now();
        if (fecha.isAfter(hoy)){
            throw new Exception("Ingrese una fecha correcta");
        }
        
        List<Venta> ventasDelDia = new ArrayList<>();

        for (Venta venta : this.getVentas()) {
            if (venta.getFecha().toLocalDate().equals(fecha)) {
                ventasDelDia.add(venta);
            }
        }
        return ventasDelDia;
    }

    @Override
    public String getNumeroVentasYMontoTotalPorFecha(LocalDate fecha) throws Exception{
        Double monto_total = getMontoTotalDeVentasPorFecha(fecha);
        int cantidad_ventas = getNumeroDeVentasPorFecha(fecha);
        
        return "Cantidad de ventas: " + cantidad_ventas + "\nMonto: " + monto_total;
    }

    @Override
    public Venta getVentaMasAlta() {
        Venta ventaMayor = null;
        Double mayor = 0.0;
        
        for (Venta venta : this.getVentas()){
            if (venta.getTotal() >= mayor){
                mayor = venta.getTotal();
                ventaMayor = venta;
            }
        }
        return ventaMayor;
    }
}
