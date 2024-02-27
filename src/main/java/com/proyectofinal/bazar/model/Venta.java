package com.proyectofinal.bazar.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Entity
public class Venta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long codigo;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonIgnore
    private LocalDateTime fecha;
    
    @JsonIgnore
    private Double total;
    
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "venta_lista_productos", 
               joinColumns = @JoinColumn(name = "codigo_venta"),
               inverseJoinColumns = @JoinColumn(name = "codigo_producto"))
    private List<Producto> listaProductos;
    
    @OneToOne
    @JoinColumn(name="id_cliente", referencedColumnName = "identificacion")
    private Cliente cliente;
    
    public Venta() {
        LocalDateTime hoy = LocalDateTime.now(ZoneId.of("-05:00"));
        fecha = darFormatoAFecha(hoy);
        total = 0.0;
    }

    public Venta(Long codigo, List<Producto> listaProductos, Cliente cliente) {
        this.codigo = codigo;
        LocalDateTime hoy = LocalDateTime.now(ZoneId.of("-05:00"));
        fecha = darFormatoAFecha(hoy);
        total = 0.0;
        this.listaProductos = listaProductos;
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return "Venta{" + "codigo_venta=" + codigo 
             + ", fecha_venta=" + fecha + ", total=" + total 
             + ", listaProductos=" + listaProductos + ", cliente=" + cliente + '}';
    }
    
    public void calcularValorTotal(Map<Long, Double> cantidadVendidaPorProducto){
        for (Producto producto: listaProductos){
            Double totalPorProducto = (producto.getCosto() * cantidadVendidaPorProducto.get(producto.getCodigo()));
            total += totalPorProducto;
        }
    }
    
    private LocalDateTime darFormatoAFecha(LocalDateTime fecha){
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String fechaFormateada = fecha.format(f);
        fecha = LocalDateTime.parse(fechaFormateada, f);
        return fecha;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    @JsonProperty
    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    @JsonProperty
    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<Producto> getListaProductos() {
        return listaProductos;
    }

    @JsonProperty
    public void setListaProductos(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
}
