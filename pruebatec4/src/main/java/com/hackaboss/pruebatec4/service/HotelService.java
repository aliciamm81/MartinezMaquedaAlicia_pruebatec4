package com.hackaboss.pruebatec4.service;

import com.hackaboss.pruebatec4.exception.HotelException;
import com.hackaboss.pruebatec4.model.Hotel;
import com.hackaboss.pruebatec4.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class HotelService implements IHotelService {

    @Autowired
    HotelRepository hotelRepository;

    @Override
    public void createHotel(Hotel hotel) {
        hotelRepository.save(hotel);
    }

    public Hotel findHotelById(Long id) throws HotelException {
        Hotel hotel = hotelRepository.findById(id).orElse(null);
        if (hotel == null) {
            throw new HotelException("El hotel con el id introducido no se encuentra en la base de datos");
        }
        return hotel;
    }

    public List<Hotel> readHoteles() throws HotelException {
        List<Hotel> hoteles = hotelRepository.findAll()
                .stream()
                .filter(hotel -> hotel.getFechaBaja() == null)
                .toList();

        if (hoteles.isEmpty()) {
            throw new HotelException("No hay hoteles registrados en la base de datos");
        }
        return hoteles;
    }

    public Hotel findHotelByCodigo(String codigo) throws HotelException {
        Hotel hotel = hotelRepository.findByCodigo(codigo).orElse(null);
        if (hotel == null) {
            throw new HotelException("El codigo de hotel introducido no se encuentra en la base de datos");
        }
        return hotel;
    }

    @Override
    public List<Hotel> findHotelesDisponiblesByFechaYCiudad(LocalDate fechaDesde, LocalDate fechaHasta, String ciudad) throws HotelException {
        List<Hotel> hotelesDisponibles = hotelRepository.findHotelesDisponiblesByFechaYCiudad(fechaDesde, fechaHasta, ciudad)
                .stream()
                .filter(hotel -> hotel.getFechaBaja() == null)
                .toList();

        if (hotelesDisponibles.isEmpty()) {
            throw new HotelException("No hay hoteles que cumplan estos criterios en la base de datos");
        }

        return hotelesDisponibles;
    }

    @Override
    public boolean existsReservasAfterByHotel(Long idHotel, LocalDate fechaActual) {
        return hotelRepository.existsReservasAfterByHotel(idHotel, fechaActual);
    }

}
