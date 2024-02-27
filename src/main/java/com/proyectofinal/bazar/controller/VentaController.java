/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectofinal.bazar.controller;

import com.proyectofinal.bazar.model.Producto;
import com.proyectofinal.bazar.model.Venta;
import com.proyectofinal.bazar.service.VentaService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    VentaService ventaService;

    @PostMapping("/crear")
    public ResponseEntity saveVenta(@RequestBody Venta venta) {
        try {
            ventaService.saveVenta(venta);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/traer-todas")
    public List<Venta> getVentas() {
        List<Venta> listaVentas = ventaService.getVentas();
        return listaVentas;
    }

    @GetMapping("/{id}")
    public ResponseEntity findVentaById(@PathVariable Long id) {
        Venta venta;
        try {
            venta = ventaService.findVentaById(id);
        } catch (NullPointerException ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(venta, HttpStatus.OK) ;
    }
    
    @DeleteMapping("/eliminar/{id}")
    public void deleteVenta(@PathVariable Long id){
        ventaService.deleteVenta(id);
    }
    
    @GetMapping("/productos/{id_venta}")
    public ResponseEntity getProductosByVenta(@PathVariable Long id_venta) {
        List<Producto> productos;
        try {
            productos = ventaService.getProductosByVenta(id_venta);
        } catch (NullPointerException ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(productos, HttpStatus.OK);
    }
    
    @GetMapping("cantidad/{fecha}")
    public ResponseEntity getMontoYCantidadVentasPorFecha(@PathVariable LocalDate fecha){
        String msg;
        try {
             msg = ventaService.getNumeroVentasYMontoTotalPorFecha(fecha);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(msg, HttpStatus.OK);
    }
    
    @GetMapping("/mayor_venta")
    public Venta getVentaMasAlta(){
        return ventaService.getVentaMasAlta();
    }
}
