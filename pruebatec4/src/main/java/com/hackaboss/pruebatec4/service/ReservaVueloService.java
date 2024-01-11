package com.hackaboss.pruebatec4.service;

import com.hackaboss.pruebatec4.exception.VueloException;
import com.hackaboss.pruebatec4.model.ReservaVuelo;
import com.hackaboss.pruebatec4.repository.ReservaVueloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaVueloService implements IReservaVueloService {

    @Autowired
    ReservaVueloRepository reservaVueloRepository;

    @Override
    public void create(ReservaVuelo reservaVuelo) {
        reservaVueloRepository.save(reservaVuelo);
    }

    @Override
    public ReservaVuelo findReservaVueloById(Long id) throws VueloException {
        ReservaVuelo reservaVuelo = reservaVueloRepository.findById(id).orElse(null);
        if (reservaVuelo == null) {
            throw new VueloException("No se encontró la reserva con el id: " + id);
        }
        return reservaVuelo;
    }

    @Override
    public List<ReservaVuelo> readReservasVuelos() throws VueloException {
        List<ReservaVuelo> reservas = reservaVueloRepository.findAll()
                .stream()
                .filter(reservaVuelo -> reservaVuelo.getFechaBaja() == null)
                .toList();

        if (reservas.isEmpty()) {
            throw new VueloException("No hay reservas de vuelos registradas en la base de datos");
        }

        return reservas;
    }

}
