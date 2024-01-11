package com.hackaboss.pruebatec4.repository;

import com.hackaboss.pruebatec4.model.ReservaHotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservaHotelRepository extends JpaRepository<ReservaHotel, Long> {
    @Query("SELECT rh FROM ReservaHotel rh " +
            "WHERE rh.habitacion.id = :idHabitacion " +
            "AND (rh.fechaDesde <= :fechaHasta AND rh.fechaHasta >= :fechaDesde) " +
            "AND (rh.fechaBaja IS NULL OR (rh.fechaBaja >= :fechaDesde AND rh.fechaBaja <= :fechaHasta))")
    Optional<List<ReservaHotel>> findReservasByFechaAndHabitacion(LocalDate fechaDesde, LocalDate fechaHasta, Long idHabitacion);

    @Query("SELECT rh FROM ReservaHotel rh JOIN rh.habitacion h WHERE h.hotel.id = :idHotel ")
    Optional<List<ReservaHotel>> findReservaByIdHotel(Long idHotel);
}
