package com.hackaboss.pruebatec4.service;

import com.hackaboss.pruebatec4.exception.HotelException;
import com.hackaboss.pruebatec4.model.ReservaHotel;

import java.time.LocalDate;
import java.util.List;

public interface IReservaHotelService {

    void createReservaHotel(ReservaHotel reservaHotel);

    ReservaHotel findReservaHotelById(Long id) throws HotelException;

    List<ReservaHotel> findReservaByIdHotel(Long id) throws HotelException;

    List<ReservaHotel> readReservasHoteles() throws HotelException;

    List<ReservaHotel> findReservasByFechaAndHabitacion(LocalDate fechaDesde, LocalDate fechaHasta, Long idHabitacion) throws HotelException;

}
