package com.hackaboss.pruebatec4.repository;

import com.hackaboss.pruebatec4.model.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {
    Optional<Habitacion> findByCodigo(String codigo);

    @Query("SELECT h FROM Habitacion h WHERE h.hotel.id = :hotelId")
    List<Habitacion> findByHotelId(Long hotelId);
}
