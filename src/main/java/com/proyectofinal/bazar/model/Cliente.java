package com.proyectofinal.bazar.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class Cliente {
    
    @Id
    private Long identificacion;
    private String nombre;
    private String Apellido;
    private Long celular;

    public Cliente() {
    }

    public Cliente(Long identificacion, String nombre, String Apellido, Long celular) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.Apellido = Apellido;
        this.celular = celular;
    }
    
}
