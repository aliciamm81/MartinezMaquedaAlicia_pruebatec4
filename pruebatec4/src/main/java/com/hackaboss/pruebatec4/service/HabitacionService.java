package com.hackaboss.pruebatec4.service;

import com.hackaboss.pruebatec4.exception.HotelException;
import com.hackaboss.pruebatec4.model.Habitacion;
import com.hackaboss.pruebatec4.repository.HabitacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HabitacionService implements IHabitacionService {

    @Autowired
    HabitacionRepository habitacionRepository;

    @Autowired
    IHotelService hotelService;

    @Override
    public void createHabitacion(Habitacion habitacion) throws HotelException {
        if (habitacion.getHotel().getFechaBaja() != null) {
            throw new HotelException("No se pueden crear habitaciones para este hotel ya que se encuenta dado de baja");
        }
        habitacionRepository.save(habitacion);
    }

    @Override
    public List<Habitacion> readHabitacion() throws HotelException {
        List<Habitacion> habitaciones = habitacionRepository.findAll()
                .stream()
                .filter(habitacion -> habitacion.getFechaBaja() == null)
                .toList();
        if (habitaciones.isEmpty()) {
            throw new HotelException("No hay habitaciones registradas en la base de datos");
        }
        return habitaciones;
    }

    @Override
    public List<Habitacion> findHabitacionByIdHotel(Long id) throws HotelException {
        List<Habitacion> habitaciones = habitacionRepository.findByHotelId(id).stream()
                .filter(habitacion -> habitacion.getHotel().getFechaBaja() == null)
                .toList();
        if (habitaciones.isEmpty()) {
            throw new HotelException("No hay habitaciones registradas para este hotel en la base de datos");
        }
        return habitaciones;
    }


    @Override
    public Habitacion findHabitacionById(Long id) throws HotelException {
        Habitacion habitacion = habitacionRepository.findById(id).orElse(null);
        if (habitacion == null) {
            throw new HotelException("No existe ninguna habitación con este id");
        }
        return habitacion;
    }

    public Habitacion findHabitacionByCodigo(String codigo) throws HotelException {
        Habitacion habitacion = habitacionRepository.findByCodigo(codigo).orElse(null);
        if (habitacion == null) {
            throw new HotelException("No existe ninguna habitación con esta numeración");
        }
        if (habitacion.getHotel().getFechaBaja() != null) {
            throw new HotelException("El hotel seleccionado se encuentra dado de baja del sistema");
        }
        return habitacion;
    }

}
