package com.proyectofinal.bazar.model;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class VentaDTO{
    
    private Long codigo;
    private LocalDateTime fecha;
    private Double total;
    private Cliente cliente;

    public VentaDTO() {
    }

    public VentaDTO(Long codigo, LocalDateTime fecha, Double total, Cliente cliente) {
        this.codigo = codigo;
        this.fecha = fecha;
        this.total = total;
        this.cliente = cliente;
    }

    
    
}
