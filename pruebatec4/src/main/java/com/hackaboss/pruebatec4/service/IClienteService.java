package com.hackaboss.pruebatec4.service;

import com.hackaboss.pruebatec4.exception.ClienteException;
import com.hackaboss.pruebatec4.model.Cliente;

public interface IClienteService {

    public void create(Cliente cliente);

    public Cliente findByDni(String dni) throws ClienteException;

    Cliente updateByDni(String dni) throws ClienteException;
}
