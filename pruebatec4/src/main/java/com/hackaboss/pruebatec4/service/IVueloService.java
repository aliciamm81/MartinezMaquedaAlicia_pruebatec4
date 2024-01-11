package com.hackaboss.pruebatec4.service;

import com.hackaboss.pruebatec4.exception.VueloException;
import com.hackaboss.pruebatec4.model.Vuelo;

import java.time.LocalDate;
import java.util.List;

public interface IVueloService {

    void create(Vuelo vuelo);

    Vuelo findVueloById(Long id) throws VueloException;

    List<Vuelo> readVuelos() throws VueloException;

    List<Vuelo> findVuelosDisponibles(LocalDate fechaDesde,
                                      LocalDate fechaHasta,
                                      String origen,
                                      String destino) throws VueloException;


    Vuelo findVuelosByFechaAndOrigenDestino(LocalDate fecha,
                                            String origen,
                                            String destino,
                                            Integer numPersonas) throws VueloException;

    boolean existsReservasPosterioresParaVuelo(Long idVuelo, LocalDate fechaActual);

    void incrementarAsientosDisponibles(String tipoAsiento, Integer numPersonas, Long idVuelo) throws VueloException;

    void descontarAsientosDisponibles(String tipoAsiento, Integer numPersonas, Long idVuelo) throws VueloException;

    Double precioTotalReserva(String tipoAsiento, Integer numPersonas, Long idVuelo) throws VueloException;
}
