package com.proyectofinal.bazar.service;

import com.proyectofinal.bazar.model.Cliente;
import java.util.List;

public interface ClienteService {
    public void saveCliente(Cliente cliente);
    public Cliente findClienteById(Long id);
    public void updateCliente(Cliente cliente, Long id) throws Exception;
    public void deleteCliente(Long id);
    public List<Cliente> getClientes();
}
