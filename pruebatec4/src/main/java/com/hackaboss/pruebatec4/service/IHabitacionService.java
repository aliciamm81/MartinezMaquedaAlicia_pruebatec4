package com.hackaboss.pruebatec4.service;

import com.hackaboss.pruebatec4.exception.HotelException;
import com.hackaboss.pruebatec4.model.Habitacion;

import java.util.List;

public interface IHabitacionService {

    void createHabitacion(Habitacion habitacion) throws HotelException;

    List<Habitacion> readHabitacion() throws HotelException;

    List<Habitacion> findHabitacionByIdHotel(Long id) throws HotelException;

    Habitacion findHabitacionById(Long id) throws HotelException;

    Habitacion findHabitacionByCodigo(String codigo) throws HotelException;

}
