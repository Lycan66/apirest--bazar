package com.proyectofinal.bazar.service;

import com.proyectofinal.bazar.model.Producto;
import com.proyectofinal.bazar.model.Venta;
import java.time.LocalDate;
import java.util.List;

public interface VentaService {
    public void saveVenta(Venta venta) throws Exception;
    public Venta findVentaById(Long id);
    public void deleteVenta(Long id);
    public List<Venta> getVentas();
    public List<Producto> getProductosByVenta(Long id_venta) throws NullPointerException;
    public Double getMontoTotalDeVentasPorFecha(LocalDate fecha) throws Exception;
    public int getNumeroDeVentasPorFecha(LocalDate fecha) throws Exception;
    public String getNumeroVentasYMontoTotalPorFecha(LocalDate fecha) throws Exception;
    public Venta getVentaMasAlta();
}
