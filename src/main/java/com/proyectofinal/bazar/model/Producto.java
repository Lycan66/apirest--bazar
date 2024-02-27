package com.proyectofinal.bazar.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import java.util.List;

@Entity
public class Producto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long codigo;
    private String nombre;
    private String marca;
    private Double costo;
    private Double cantidad_disponible;
    private Double cantidadVendida;
    
    @JsonIgnore
    @ManyToMany(mappedBy = "listaProductos", fetch = FetchType.LAZY)
    private List<Venta> ventas;

    public Producto() {
    }

    public Producto(Long codigo, String nombre, String marca, Double costo, Double cantidad_disponible, Double cantidadVendida, List<Venta> ventas) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.marca = marca;
        this.costo = costo;
        this.cantidad_disponible = cantidad_disponible;
        this.cantidadVendida = cantidadVendida;
        this.ventas = ventas;
    }


    public void descontarStock(Double cantidad) throws Exception{
        if(cantidad <= cantidad_disponible){
            cantidad_disponible -= cantidad;
        }
        else{
            throw new Exception("No se pudo procesar la solicitud. "
                                + "Por favor revisar la cantidad disponible del producto " 
                                + codigo + ", " + nombre);
        }
    }
    
    @Override
    public String toString() {
        return "Producto{" + "codigo_producto=" + codigo + ", nombre=" + nombre + ", marca=" + marca + ", costo=" + costo + ", cantidad_disponible=" + cantidad_disponible + ", cantidadVendida=" + cantidadVendida + '}';
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public Double getCantidad_disponible() {
        return cantidad_disponible;
    }

    public void setCantidad_disponible(Double cantidad_disponible) {
        this.cantidad_disponible = cantidad_disponible;
    }

    @JsonIgnore
    public Double getCantidadVendida() {
        if(cantidadVendida == null){
            return 0.0;
        }
        return cantidadVendida;
    }

    @JsonProperty
    public void setCantidadVendida(Double cantidadVendida) {
        this.cantidadVendida = cantidadVendida;
    }

    public List<Venta> getVentas() {
        return ventas;
    }

    public void setVentas(List<Venta> ventas) {
        this.ventas = ventas;
    }
    
    public boolean isOutOfStock(){
        return cantidad_disponible < 5;
    }
}
