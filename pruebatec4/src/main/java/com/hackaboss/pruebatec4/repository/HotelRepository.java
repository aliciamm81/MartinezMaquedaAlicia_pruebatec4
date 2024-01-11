package com.hackaboss.pruebatec4.repository;

import com.hackaboss.pruebatec4.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    Optional<Hotel> findByCodigo(String codigo);

    @Query("SELECT DISTINCT h FROM Hotel h " +
            "JOIN h.habitaciones hab " +
            "LEFT JOIN hab.reservasHotel res " +
            "WHERE h.ciudad = :ciudad " +
            "AND (" +
            "   (res.fechaHasta < :fechaInicio OR res.fechaDesde > :fechaFin) " +
            "   OR res IS NULL" +
            ") " +
            "AND hab IS NOT NULL ")
    List<Hotel> findHotelesDisponiblesByFechaYCiudad(
            LocalDate fechaInicio,
            LocalDate fechaFin,
            String ciudad
    );

    @Query("SELECT COUNT(r) > 0 FROM ReservaHotel r " +
            "JOIN r.habitacion hab JOIN hab.hotel h " +
            "WHERE h.id = :idHotel AND r.fechaDesde > :fechaActual")
    boolean existsReservasAfterByHotel(Long idHotel, LocalDate fechaActual);

}
