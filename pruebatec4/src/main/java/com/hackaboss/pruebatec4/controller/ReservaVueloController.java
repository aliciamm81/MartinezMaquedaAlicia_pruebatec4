package com.hackaboss.pruebatec4.controller;

import com.hackaboss.pruebatec4.dto.ReservaVueloDTO;
import com.hackaboss.pruebatec4.dto.ReservaVueloUpdateDTO;
import com.hackaboss.pruebatec4.exception.ClienteException;
import com.hackaboss.pruebatec4.exception.VueloException;
import com.hackaboss.pruebatec4.model.Cliente;
import com.hackaboss.pruebatec4.model.ReservaVuelo;
import com.hackaboss.pruebatec4.model.Vuelo;
import com.hackaboss.pruebatec4.service.IClienteService;
import com.hackaboss.pruebatec4.service.IReservaVueloService;
import com.hackaboss.pruebatec4.service.IVueloService;
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

@RestController
@RequestMapping("/agencia/reserva-vuelo")
public class ReservaVueloController {

    @Autowired
    IReservaVueloService reservaVueloService;

    @Autowired
    IClienteService clienteService;

    @Autowired
    IVueloService vueloService;

    @Operation(summary = "Crear nueva reserva de vuelo", description = "Crea una nueva reserva de vuelo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Éxito - Reserva creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta - No hay disponibilidad de asientos"),
            @ApiResponse(responseCode = "404", description = "No encontrado - No existe ningún registro con este ID"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @PostMapping(value = "/nuevo", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createReserva(@Valid @RequestBody ReservaVueloDTO reservaVueloDTO) {
        try {
            Cliente cliente = getOrCreateClienteFromDTO(reservaVueloDTO);
            ReservaVuelo reserva = setReservaVueloFromDTO(reservaVueloDTO, cliente);
            vueloService.descontarAsientosDisponibles(reservaVueloDTO.getTipoAsiento(), reservaVueloDTO.getNumPersonas(), reserva.getVuelo().getId());
            reservaVueloService.create(reserva);

            return new ResponseEntity<>("Reserva creada correctamente, precio total: " + reserva.getPrecio(), HttpStatus.OK);
        } catch (VueloException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Obtener todas las reservas de vuelo", description = "Obtiene todas las reservas de vuelo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Éxito - Se obtuvieron las reservas"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta - No hay reservas registradas en la base de datos"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> readReservasVuelos() {
        try {
            return new ResponseEntity<>(reservaVueloService.readReservasVuelos(), HttpStatus.OK);
        } catch (VueloException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Obtener reserva de vuelo por ID", description = "Obtiene una reserva de vuelo por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Éxito - Se encontró la reserva"),
            @ApiResponse(responseCode = "404", description = "No encontrado - No existe ningún registro con este ID"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findReservaVueloById(@Valid @PathVariable Long id) {
        try {
            return new ResponseEntity<>(reservaVueloService.findReservaVueloById(id), HttpStatus.OK);
        } catch (VueloException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Actualizar reserva de vuelo por ID", description = "Actualiza una reserva de vuelo por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Éxito - Reserva actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta - No se pudo actualizar la reserva ya que se encuentra dada de baja"),
            @ApiResponse(responseCode = "404", description = "No encontrado - No existe ningún hotel con este ID"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @PutMapping(value = "/actualizar/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateReserva(@PathVariable Long id,
                                           @Valid @RequestBody ReservaVueloUpdateDTO reservaVueloUpdateDTO) {
        try {
            ReservaVuelo reserva = reservaVueloService.findReservaVueloById(id);
            if (reserva.getFechaBaja() != null) {
                return new ResponseEntity<>("La reserva seleccionado no se puede actualizar ya que está dada de baja", HttpStatus.BAD_REQUEST);
            }
            vueloService.incrementarAsientosDisponibles(reserva.getTipoAsiento(), reserva.getNumPersonas(), reserva.getVuelo().getId());
            reserva.setTipoAsiento(reservaVueloUpdateDTO.getTipoAsiento());
            reserva.setNumPersonas(reservaVueloUpdateDTO.getNumPersonas());
            reserva.setPrecio(vueloService.precioTotalReserva(reserva.getTipoAsiento(), reserva.getNumPersonas(), reserva.getVuelo().getId()));
            vueloService.descontarAsientosDisponibles(reservaVueloUpdateDTO.getTipoAsiento(), reservaVueloUpdateDTO.getNumPersonas(), reserva.getVuelo().getId());
            reservaVueloService.create(reserva);

            return new ResponseEntity<>("Reserva actualizada correctamente el precio es: " + reserva.getPrecio(), HttpStatus.OK);
        } catch (VueloException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Eliminar reserva de vuelo por ID", description = "Elimina una reserva de vuelo por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Éxito - Reserva eliminada correctamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta - La reserva ya se encuentra dada de baja"),
            @ApiResponse(responseCode = "404", description = "No encontrado - No existe ningún hotel con este ID"),
            @ApiResponse(responseCode = "500", description = "Error del servidor")
    })
    @DeleteMapping(value = "/eliminar/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteReservaVueloById(@Valid @PathVariable Long id) {
        try {
            ReservaVuelo reserva = reservaVueloService.findReservaVueloById(id);
            if (reserva.getFechaBaja() != null) {
                return new ResponseEntity<>("La reserva seleccionada ya se encuentra dado de baja", HttpStatus.BAD_REQUEST);
            }
            vueloService.incrementarAsientosDisponibles(reserva.getTipoAsiento(), reserva.getNumPersonas(), reserva.getVuelo().getId());
            reserva.setUsuarioBaja(SecurityContextHolder.getContext().getAuthentication().getName());
            reserva.setFechaBaja(LocalDate.now());
            reservaVueloService.create(reserva);

            return new ResponseEntity<>("Reserva eliminada correctamente", HttpStatus.OK);
        } catch (VueloException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Este método permite crear un cliente en la base de datos a partir de un DTO
     * pasado como parámetro. Comprueba si el clienbte ya existe en la base de datos
     * y si no existe se crea.
     *
     * @param reservaVueloDTO
     * @return
     */
    private Cliente getOrCreateClienteFromDTO(ReservaVueloDTO reservaVueloDTO) {
        try {
            return clienteService.findByDni(reservaVueloDTO.getDni());
        } catch (ClienteException e) {
            Cliente cliente = new Cliente();
            cliente.setNumTarjeta(reservaVueloDTO.getNumTarjeta());
            cliente.setNombre(reservaVueloDTO.getNombre());
            cliente.setApellido(reservaVueloDTO.getApellido());
            cliente.setEmail(reservaVueloDTO.getEmail());
            cliente.setTelefono(reservaVueloDTO.getTelefono());
            cliente.setDni(reservaVueloDTO.getDni());
            clienteService.create(cliente);
            return cliente;
        }
    }

    /**
     * Este método permite setear los atributos de un objeto reservaVuelo en base a uno DTO pasado como parámetro.
     *
     * @param reservaVueloDTO
     * @param cliente
     * @return
     * @throws VueloException
     */
    private ReservaVuelo setReservaVueloFromDTO(ReservaVueloDTO reservaVueloDTO, Cliente cliente) throws VueloException {
        try {
            ReservaVuelo reserva = new ReservaVuelo();
            Vuelo vuelo = vueloService.findVuelosByFechaAndOrigenDestino(reservaVueloDTO.getFecha(),
                    reservaVueloDTO.getOrigen(),
                    reservaVueloDTO.getDestino(),
                    reservaVueloDTO.getNumPersonas());
            reserva.setUsuarioAlta(SecurityContextHolder.getContext().getAuthentication().getName());
            reserva.setFechaAlta(LocalDate.now());
            reserva.setNumPersonas(reservaVueloDTO.getNumPersonas());
            reserva.setTipoAsiento(reservaVueloDTO.getTipoAsiento());
            reserva.setCliente(cliente);
            reserva.setVuelo(vuelo);
            reserva.setPrecio(vueloService.precioTotalReserva(reserva.getTipoAsiento(), reserva.getNumPersonas(), vuelo.getId()));

            return reserva;
        } catch (VueloException e) {
            throw new VueloException(e.getMessage());
        }
    }

}
