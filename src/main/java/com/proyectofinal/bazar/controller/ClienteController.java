package com.proyectofinal.bazar.controller;

import com.proyectofinal.bazar.model.Cliente;
import com.proyectofinal.bazar.service.ClienteService;
import java.util.List;
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
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    @PostMapping("/crear")
    public ResponseEntity saveCliente(@RequestBody Cliente cliente) {
        clienteService.saveCliente(cliente);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/traer")
    public List<Cliente> getClientes() {
        List<Cliente> listaClientes = clienteService.getClientes();
        return listaClientes;
    }

    @GetMapping("/{id_cliente}")
    public ResponseEntity getCliente(@PathVariable Long id_cliente) {
        
        Cliente cliente;
        try {
            cliente = clienteService.findClienteById(id_cliente);
        } catch (NullPointerException e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(cliente,HttpStatus.OK);
    }
    
    @DeleteMapping("/eliminar/{id_cliente}")
    public void deleteCliente(@PathVariable Long id_cliente){
        clienteService.deleteCliente(id_cliente);
    }
    
    @PutMapping("/editar/{id_cliente}")
    public ResponseEntity updateCliente(@PathVariable Long id_cliente, 
                                        @RequestBody Cliente cliente) {
        
        try{
            clienteService.updateCliente(cliente, id_cliente);
        }catch(Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        
        return new ResponseEntity(cliente, HttpStatus.OK);
    }
}
