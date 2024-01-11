package com.hackaboss.pruebatec4.service;

import com.hackaboss.pruebatec4.exception.HotelException;
import com.hackaboss.pruebatec4.model.Hotel;

import java.time.LocalDate;
import java.util.List;

public interface IHotelService {

    void createHotel(Hotel hotel);

    Hotel findHotelById(Long id) throws HotelException;

    List<Hotel> readHoteles() throws HotelException;

    Hotel findHotelByCodigo(String codigo) throws HotelException;

    List<Hotel> findHotelesDisponiblesByFechaYCiudad(LocalDate fechaDesde, LocalDate fechaHasta, String ciudad) throws HotelException;

    boolean existsReservasAfterByHotel(Long idHotel, LocalDate fechaActual);
}
