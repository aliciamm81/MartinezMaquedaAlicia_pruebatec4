package com.hackaboss.pruebatec4.service;

import com.hackaboss.pruebatec4.exception.HotelException;
import com.hackaboss.pruebatec4.model.ReservaHotel;
import com.hackaboss.pruebatec4.repository.ReservaHotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservaHotelService implements IReservaHotelService {

    @Autowired
    ReservaHotelRepository reservaHotelRepository;

    @Override
    public void createReservaHotel(ReservaHotel reservaHotel) {
        reservaHotelRepository.save(reservaHotel);
    }

    @Override
    public ReservaHotel findReservaHotelById(Long id) throws HotelException {
        ReservaHotel reservaHotel = reservaHotelRepository.findById(id).orElse(null);
        if (reservaHotel == null) {
            throw new HotelException("No se encontró la reserva con el id: " + id);
        }
        return reservaHotel;
    }

    @Override
    public List<ReservaHotel> findReservaByIdHotel(Long id) throws HotelException {
        List<ReservaHotel> reservas = reservaHotelRepository.findReservaByIdHotel(id).orElse(null);
        if (reservas == null) {
            throw new HotelException("No se encontró el hotel con el id: " + id);
        }
        return reservas.stream().filter(reservaHotel -> reservaHotel.getFechaBaja() != null).toList();
    }

    public List<ReservaHotel> readReservasHoteles() throws HotelException {
        List<ReservaHotel> reservas = reservaHotelRepository.findAll()
                .stream()
                .filter(reservaHotel -> reservaHotel.getFechaBaja() == null)
                .toList();

        if (reservas.isEmpty()) {
            throw new HotelException("No hay reservas registradas en la base de datos");
        }

        return reservas;
    }

    @Override
    public List<ReservaHotel> findReservasByFechaAndHabitacion(LocalDate fechaDesde, LocalDate fechaHasta, Long idHabitacion) throws HotelException {
        List<ReservaHotel> reservas = reservaHotelRepository
                .findReservasByFechaAndHabitacion(fechaDesde, fechaHasta, idHabitacion)
                .orElse(null);
        if (reservas == null) {
            throw new HotelException("No se encontró la habitación con el id: " + idHabitacion);
        }
        return reservas;
    }
}
