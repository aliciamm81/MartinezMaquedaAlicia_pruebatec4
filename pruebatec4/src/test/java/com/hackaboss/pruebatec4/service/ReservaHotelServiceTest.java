package com.hackaboss.pruebatec4.service;

import com.hackaboss.pruebatec4.model.Habitacion;
import com.hackaboss.pruebatec4.model.ReservaHotel;
import com.hackaboss.pruebatec4.repository.ReservaHotelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

public class ReservaHotelServiceTest {

    @Mock
    private ReservaHotelRepository reservaHotelRepository;

    @InjectMocks
    private ReservaHotelService reservaHotelService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCrearReservaHotelExitosa() {

        ReservaHotel reservaMock = new ReservaHotel();

        when(reservaHotelRepository.save(any())).thenReturn(reservaMock);

        ReservaHotel nuevaReserva = new ReservaHotel();
        nuevaReserva.setFechaDesde(LocalDate.now());
        nuevaReserva.setFechaHasta(LocalDate.now());
        nuevaReserva.setHabitacion(new Habitacion());
        nuevaReserva.setNumPersonas(1);
        nuevaReserva.setPrecio(1000.0);
        nuevaReserva.setNoches(1);
        reservaHotelService.createReservaHotel(nuevaReserva);

        verify(reservaHotelRepository, times(1)).save(nuevaReserva);
    }

}

