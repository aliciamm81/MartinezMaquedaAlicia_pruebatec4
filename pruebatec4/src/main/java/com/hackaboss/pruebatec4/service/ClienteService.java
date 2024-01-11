package com.hackaboss.pruebatec4.service;

import com.hackaboss.pruebatec4.exception.ClienteException;
import com.hackaboss.pruebatec4.model.Cliente;
import com.hackaboss.pruebatec4.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService implements IClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Override
    public void create(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    @Override
    public Cliente findByDni(String dni) throws ClienteException {
        Cliente cliente = clienteRepository.findByDni(dni).orElse(null);
        if (cliente == null) {
            throw new ClienteException("El cliente no existe");
        }
        return cliente;
    }

    @Override
    public Cliente updateByDni(String dni) throws ClienteException {
        Cliente cliente = clienteRepository.findByDni(dni).orElse(null);
        if (cliente == null) {
            throw new ClienteException("El cliente no existe");
        }
        return cliente;
    }
}
