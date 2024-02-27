package com.proyectofinal.bazar.service;

import com.proyectofinal.bazar.model.Cliente;
import com.proyectofinal.bazar.repository.ClienteRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImp implements ClienteService{

    @Autowired
    ClienteRepository clienteRepo;
    
    @Override
    public void saveCliente(Cliente cliente) {
        clienteRepo.save(cliente);
    }

    @Override
    public Cliente findClienteById(Long id) throws NullPointerException{
        Cliente cliente = clienteRepo.findById(id).orElse(null);
        
        if (cliente == null){
            throw new NullPointerException("Cliente con ID " + id + " no encontrado");
        }
        return cliente;
    }

    @Override
    public void updateCliente(Cliente upCliente, Long id) throws Exception{
        if(clienteRepo.existsById(id)){
            this.saveCliente(upCliente);
        }
        else{
            throw new Exception ("No se pudo realizar esta acción, por favor revise el ID");
        }
    }

    @Override
    public void deleteCliente(Long id) {
        clienteRepo.deleteById(id);
    }

    @Override
    public List<Cliente> getClientes() {
        return clienteRepo.findAll();
    }
    
}
