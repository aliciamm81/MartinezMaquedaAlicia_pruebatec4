package com.hackaboss.pruebatec4.repository;

import com.hackaboss.pruebatec4.model.Vuelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VueloRepository extends JpaRepository<Vuelo, Long> {

    @Query("SELECT v FROM Vuelo v " +
            "WHERE v.origen = :origen " +
            "AND v.destino = :destino " +
            "AND v.fecha >= :fechaDesde " +
            "AND v.fecha <= :fechaHasta " +
            "AND (v.asientosEconomicosDisponibles > 0 OR v.asientosPremiumDisponibles > 0) " +
            "AND v.fechaBaja IS NULL ")
    Optional<List<Vuelo>> findVuelosDisponibles(
            LocalDate fechaDesde,
            LocalDate fechaHasta,
            String origen,
            String destino
    );

    @Query("SELECT v FROM Vuelo v " +
            "WHERE v.origen = :origen AND v.destino = :destino " +
            "AND v.fecha = :fecha " +
            "AND (v.asientosEconomicosDisponibles >= :numPersonas OR v.asientosPremiumDisponibles >= :numPersonas) " +
            "AND v.fechaBaja is null ")
    Optional<List<Vuelo>> findVuelosByFechaAndOrigenDestino(
            LocalDate fecha,
            String origen,
            String destino,
            Integer numPersonas
    );

    @Query("SELECT CASE WHEN COUNT(rv) > 0 THEN true ELSE false END " +
            "FROM ReservaVuelo rv " +
            "WHERE rv.vuelo.id = :idVuelo AND rv.vuelo.fecha > :fechaActual")
    boolean existsReservasPosterioresParaVuelo(Long idVuelo, LocalDate fechaActual);

}
