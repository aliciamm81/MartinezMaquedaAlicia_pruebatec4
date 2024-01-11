package com.hackaboss.pruebatec4.service;

import com.hackaboss.pruebatec4.exception.VueloException;
import com.hackaboss.pruebatec4.model.Vuelo;
import com.hackaboss.pruebatec4.repository.VueloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VueloService implements IVueloService {

    @Autowired
    VueloRepository vueloRepository;

    @Override
    public void create(Vuelo vuelo) {
        vueloRepository.save(vuelo);
    }

    @Override
    public Vuelo findVueloById(Long id) throws VueloException {
        Vuelo vuelo = vueloRepository.findById(id).orElse(null);
        if (vuelo == null) {
            throw new VueloException("El vuelo no existe en la base de datos");
        }
        return vuelo;
    }

    @Override
    public List<Vuelo> readVuelos() throws VueloException {
        List<Vuelo> vuelos = vueloRepository.findAll()
                .stream()
                .filter(vuelo -> vuelo.getFechaBaja() == null)
                .toList();
        if (vuelos.isEmpty()) {
            throw new VueloException("No hay vuelos registrados en la base de datos");
        }

        return vuelos;
    }

    @Override
    public List<Vuelo> findVuelosDisponibles(LocalDate fechaDesde,
                                             LocalDate fechaHasta,
                                             String origen,
                                             String destino) throws VueloException {
        List<Vuelo> vuelos = vueloRepository.findVuelosDisponibles(fechaDesde, fechaHasta, origen, destino).orElse(null);

        if (vuelos.isEmpty()) {
            throw new VueloException("No hay vuelos que cumplan estos criterios en la base de datos");
        }

        return vuelos;
    }

    @Override
    public Vuelo findVuelosByFechaAndOrigenDestino(LocalDate fecha,
                                                   String origen,
                                                   String destino,
                                                   Integer numPersonas) throws VueloException {
        List<Vuelo> vuelos = vueloRepository.findVuelosByFechaAndOrigenDestino(fecha, origen, destino, numPersonas).orElse(null);
        if (vuelos.isEmpty()) {
            throw new VueloException("No hay vuelos con el destino o fecha seleccionado");
        }
        return vuelos.stream().findFirst().orElse(null);
    }


    @Override
    public boolean existsReservasPosterioresParaVuelo(Long idVuelo, LocalDate fechaActual) {
        return vueloRepository.existsReservasPosterioresParaVuelo(idVuelo, fechaActual);
    }

    @Override
    public void incrementarAsientosDisponibles(String tipoAsiento, Integer numPersonas, Long idVuelo) throws VueloException {
        Vuelo vuelo = findVueloById(idVuelo);
        if (tipoAsiento.equalsIgnoreCase("premium")) {
            vuelo.setAsientosPremiumDisponibles(vuelo.getAsientosPremiumDisponibles() + numPersonas);
        } else {
            vuelo.setAsientosEconomicosDisponibles(vuelo.getAsientosEconomicosDisponibles() + numPersonas);
        }
    }

    @Override
    public void descontarAsientosDisponibles(String tipoAsiento, Integer numPersonas, Long idVuelo) throws VueloException {
        Vuelo vuelo = findVueloById(idVuelo);
        if (tipoAsiento.equalsIgnoreCase("premium")) {
            vuelo.setAsientosPremiumDisponibles(vuelo.getAsientosPremiumDisponibles() - numPersonas);
        } else {
            vuelo.setAsientosEconomicosDisponibles(vuelo.getAsientosEconomicosDisponibles() - numPersonas);
        }
    }

    @Override
    public Double precioTotalReserva(String tipoAsiento, Integer numPersonas, Long idVuelo) throws VueloException {
        Vuelo vuelo = findVueloById(idVuelo);
        if (tipoAsiento.equalsIgnoreCase("premium")) {
            return vuelo.getPrecioAsientoPremium() * numPersonas;
        } else {
            return vuelo.getPrecioAsientoEconomico() * numPersonas;
        }
    }

}
