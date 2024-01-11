package com.hackaboss.pruebatec4.service;

import com.hackaboss.pruebatec4.exception.HotelException;
import com.hackaboss.pruebatec4.model.Hotel;
import com.hackaboss.pruebatec4.repository.HotelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HotelServiceTest {

    @InjectMocks
    private HotelService hotelService;

    @Mock
    private HotelRepository hotelRepository;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRead() throws Exception {
        Hotel hotel1 = new Hotel();
        Hotel hotel2 = new Hotel();
        List<Hotel> listaHoteles = new ArrayList<>();
        listaHoteles.add(hotel1);
        listaHoteles.add(hotel2);
        when(hotelRepository.findAll()).thenReturn(listaHoteles);
        List<Hotel> resultado = hotelService.readHoteles();
        verify(hotelRepository, times(1)).findAll();
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
    }

    @Test
    public void testReadNoHoteles() throws Exception {
        when(hotelRepository.findAll()).thenReturn(Collections.emptyList());
        assertThrows(HotelException.class, () -> hotelService.readHoteles());
        verify(hotelRepository, times(1)).findAll();
    }

    @Test
    public void testFindHotelesDisponiblesByFechaCiudadHotelesFound() throws HotelException {
        LocalDate fechaDesde = LocalDate.now();
        LocalDate fechaHasta = LocalDate.now().plusDays(5);
        String ciudad = "Madrid";

        List<Hotel> hotelesDisponibles = new ArrayList<>();
        hotelesDisponibles.add(new Hotel());

        when(hotelRepository.findHotelesDisponiblesByFechaYCiudad(fechaDesde, fechaHasta, ciudad)).thenReturn(hotelesDisponibles);

        List<Hotel> foundHoteles = hotelService.findHotelesDisponiblesByFechaYCiudad(fechaDesde, fechaHasta, ciudad);

        assertEquals(hotelesDisponibles.size(), foundHoteles.size());
    }

    @Test
    public void testFindHotelesDisponiblesByFechaCiudadNoHotelesFound() {
        LocalDate fechaDesde = LocalDate.now();
        LocalDate fechaHasta = LocalDate.now().plusDays(5);
        String ciudad = "Madrid";

        when(hotelRepository.findHotelesDisponiblesByFechaYCiudad(fechaDesde, fechaHasta, ciudad)).thenReturn(new ArrayList<>());

        HotelException exception = assertThrows(HotelException.class, () -> {
            hotelService.findHotelesDisponiblesByFechaYCiudad(fechaDesde, fechaHasta, ciudad);
        });

        assertEquals("No hay hoteles que cumplan estos criterios en la base de datos", exception.getMessage());
    }

}


