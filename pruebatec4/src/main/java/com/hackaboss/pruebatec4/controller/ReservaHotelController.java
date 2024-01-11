package com.hackaboss.pruebatec4.controller;

import com.hackaboss.pruebatec4.dto.ReservaHotelDTO;
import com.hackaboss.pruebatec4.dto.ReservaHotelUpdateDTO;
import com.hackaboss.pruebatec4.exception.ClienteException;
import com.hackaboss.pruebatec4.exception.HotelException;
import com.hackaboss.pruebatec4.model.Cliente;
import com.hackaboss.pruebatec4.model.Habitacion;
import com.hackaboss.pruebatec4.model.ReservaHotel;
import com.hackaboss.pruebatec4.service.IClienteService;
import com.hackaboss.pruebatec4.service.IHabitacionService;
import com.hackaboss.pruebatec4.service.IHotelService;
import com.hackaboss.pruebatec4.service.IReservaHotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

@RestController
@RequestMapping("/agencia/reserva-hotel")
public class ReservaHotelController {

    @Autowired
    IReservaHotelService reservaHotelService;

    @Autowired
    IClienteService clienteService;

    @Autowired
    IHabitacionService habitacionService;

    @Autowired
    IHotelService hotelService;

    @Operation(summary = "Crear una reserva de hotel",
            description = "Este método permite crear una reserva de hotel en la base de datos seleccionando la habitacion concreta del hotel en el que se desa hospedar")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Éxito - Reserva creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta - No hay disponibilidad para las fechas solicitadas"),
            @ApiResponse(responseCode = "404", description = "No encontrado - No existe ningún registro con este ID"),
            @ApiResponse(responseCode = "500", description = "Error al intentar acceder al servidor")
    })
    @PostMapping(value = "/nuevo", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createReservaHotel(@Valid @RequestBody ReservaHotelDTO reservaHotelDTO) {
        try {
            Cliente cliente = getOrCreateClienteFromDTO(reservaHotelDTO);
            ReservaHotel reservaHotel = setReservaHotel(reservaHotelDTO, cliente);
            if (!reservaHotelService.findReservasByFechaAndHabitacion(reservaHotel.getFechaDesde(),
                    reservaHotel.getFechaHasta(),
                    reservaHotel.getHabitacion().getId()).isEmpty()) {
                return new ResponseEntity<>("No hay disponibilidad para las fechas solicitadas", HttpStatus.BAD_REQUEST);
            }
            reservaHotelService.createReservaHotel(reservaHotel);

            return new ResponseEntity<>("Reserva creada correctamente, precio total: " + reservaHotel.getPrecio(), HttpStatus.OK);
        } catch (HotelException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Obtener reservas de todos los hoteles",
            description = "Este método permite obtener todas las reservas registradas en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Éxito - Se obtuvieron las reservas"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta - No hay reservas de hoteles registradas en la base de datos"),
            @ApiResponse(responseCode = "500", description = "Error al intentar acceder al servidor")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> readReservasHoteles() {
        try {
            return new ResponseEntity<>(reservaHotelService.readReservasHoteles(), HttpStatus.OK);
        } catch (HotelException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Obtener reserva de Hoteles por ID de reservas",
            description = "Este método permite obtener una reserva por su ID de la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Éxito - Se obtuvo la reserva"),
            @ApiResponse(responseCode = "404", description = "No encontrado - No existe ningún registro con este ID"),
            @ApiResponse(responseCode = "500", description = "Error al intentar acceder al servidor")
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findReservaHotelById(@Valid @PathVariable Long id) {
        try {
            return new ResponseEntity<>(reservaHotelService.findReservaHotelById(id), HttpStatus.OK);
        } catch (HotelException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Obtener reservas por ID de hotel",
            description = "Este método permite obtener todas las reservas de un hotel concreto por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Éxito - Se obtuvo la reserva"),
            @ApiResponse(responseCode = "404", description = "No encontrado - No existe ningún hotel con este ID"),
            @ApiResponse(responseCode = "500", description = "Error al intentar acceder al servidor")
    })
    @GetMapping(value = "/hotel/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findReservaByIdHotel(@Valid @PathVariable Long id) {
        try {
            return new ResponseEntity<>(reservaHotelService.findReservaByIdHotel(id), HttpStatus.OK);
        } catch (HotelException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Actualizar reserva de hotel por ID de reserva",
            description = "Este método permite actualizar una reserva por su ID en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Éxito - Reserva actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta - No se pudo actualizar la reserva ya que se encuentra dada de baja"),
            @ApiResponse(responseCode = "404", description = "No encontrado - No existe ningún hotel con este ID"),
            @ApiResponse(responseCode = "500", description = "Error al intentar acceder al servidor")
    })
    @PutMapping(
            value = "/actualizar/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> updateReservaHotel(@PathVariable Long id,
                                                @Valid @RequestBody ReservaHotelUpdateDTO reservaHotelUpdateDTO) {
        try {
            ReservaHotel reservaHotel = reservaHotelService.findReservaHotelById(id);
            if (reservaHotel.getFechaBaja() != null) {
                return new ResponseEntity<>("La reserva seleccionado no se puede actualizar ya que está dada de baja", HttpStatus.BAD_REQUEST);
            }
            reservaHotel.setFechaDesde(reservaHotelUpdateDTO.getFechaDesde());
            reservaHotel.setFechaHasta(reservaHotelUpdateDTO.getFechaHasta());
            Integer noches = Math.toIntExact(DAYS.between(reservaHotelUpdateDTO.getFechaDesde(), reservaHotelUpdateDTO.getFechaHasta()));
            reservaHotel.setNoches(noches);
            reservaHotel.setNumPersonas(reservaHotelUpdateDTO.getNumPersonas());
            reservaHotel.setPrecio(noches * reservaHotel.getHabitacion().getPrecio());
            reservaHotelService.createReservaHotel(reservaHotel);

            return new ResponseEntity<>("Reserva actualizada correctamente", HttpStatus.OK);
        } catch (HotelException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Eliminar reserva de Hotel por ID",
            description = "Este método permite eliminar una reserva de un hotel por el ID de la reserva en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Éxito - Reserva eliminada correctamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta - La reserva ya está dada de baja"),
            @ApiResponse(responseCode = "404", description = "No encontrado - No existe ningún hotel con este ID"),
            @ApiResponse(responseCode = "500", description = "Error al intentar acceder al servidor")
    })
    @DeleteMapping(
            value = "/eliminar/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> deleteReservaById(@Valid @PathVariable Long id) {
        try {
            ReservaHotel reserva = reservaHotelService.findReservaHotelById(id);
            if (reserva.getFechaBaja() != null) {
                return new ResponseEntity<>("La reserva seleccionada ya se encuentra dada de baja", HttpStatus.BAD_REQUEST);
            }
            reserva.setFechaBaja(LocalDate.now());
            reserva.setUsuarioBaja(SecurityContextHolder.getContext().getAuthentication().getName());
            reservaHotelService.createReservaHotel(reserva);

            return new ResponseEntity<>("Reserva eliminada correctamente", HttpStatus.OK);
        } catch (HotelException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Este método permite crear un cliente en la base de datos a partir de un DTO
     * pasado como parámetro. Comprueba si el clienbte ya existe en la base de datos
     * y si no existe se crea.
     *
     * @param reservaHotelDTO
     * @return
     */
    private Cliente getOrCreateClienteFromDTO(ReservaHotelDTO reservaHotelDTO) {
        try {
            return clienteService.findByDni(reservaHotelDTO.getDni());
        } catch (ClienteException e) {
            Cliente cliente = new Cliente();
            cliente.setNumTarjeta(reservaHotelDTO.getNumTarjeta());
            cliente.setNombre(reservaHotelDTO.getNombre());
            cliente.setApellido(reservaHotelDTO.getApellido());
            cliente.setEmail(reservaHotelDTO.getEmail());
            cliente.setTelefono(reservaHotelDTO.getTelefono());
            cliente.setDni(reservaHotelDTO.getDni());
            clienteService.create(cliente);
            return cliente;
        }
    }

    /**
     * Este método permite setear los atributos de un objeto reservaHotel en base a uno DTO pasado como parámetro.
     *
     * @param reservaHotelDTO
     * @param cliente
     * @return
     * @throws HotelException
     */
    private ReservaHotel setReservaHotel(ReservaHotelDTO reservaHotelDTO, Cliente cliente) throws HotelException {

        ReservaHotel reservaHotel = new ReservaHotel();
        Habitacion habitacion = habitacionService.findHabitacionByCodigo(reservaHotelDTO.getCodigoHabitacion());
        reservaHotel.setUsuarioAlta(SecurityContextHolder.getContext().getAuthentication().getName());
        reservaHotel.setFechaAlta(LocalDate.now());
        reservaHotel.setHabitacion(habitacion);
        reservaHotel.setFechaDesde(reservaHotelDTO.getFechaDesde());
        reservaHotel.setFechaHasta(reservaHotelDTO.getFechaHasta());
        reservaHotel.setNumPersonas(reservaHotelDTO.getNumPersonas());
        Integer noches = Math.toIntExact(DAYS.between(reservaHotelDTO.getFechaDesde(), reservaHotelDTO.getFechaHasta()));
        reservaHotel.setPrecio(habitacion.getPrecio() * noches);
        reservaHotel.setNoches(noches);
        reservaHotel.setCliente(cliente);
        return reservaHotel;
    }

}
