package com.hackaboss.pruebatec4.service;

import com.hackaboss.pruebatec4.exception.VueloException;
import com.hackaboss.pruebatec4.model.ReservaVuelo;

import java.util.List;

public interface IReservaVueloService {

    void create(ReservaVuelo reservaVuelo);

    ReservaVuelo findReservaVueloById(Long id) throws VueloException;

    List<ReservaVuelo> readReservasVuelos() throws VueloException;

}
