package com.hackaboss.pruebatec4.controller;

import com.hackaboss.pruebatec4.dto.HabitacionDTO;
import com.hackaboss.pruebatec4.dto.HabitacionUpdateDTO;
import com.hackaboss.pruebatec4.exception.HotelException;
import com.hackaboss.pruebatec4.model.Habitacion;
import com.hackaboss.pruebatec4.model.Hotel;
import com.hackaboss.pruebatec4.service.IHabitacionService;
import com.hackaboss.pruebatec4.service.IHotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@Validated
@RestController
@RequestMapping("/agencia/hoteles/habitaciones")
public class HabitacionController {

    @Autowired
    IHabitacionService habitacionService;

    @Autowired
    IHotelService hotelService;

    @Operation(summary = "Crear una habitación nueva", description = "Este método permite crear una habitación nueva en la base de datos asociada al código de un hotel concreto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Éxito - Habitación creada correctamente"),
            @ApiResponse(responseCode = "409", description = "Esta habitación ya esta registrada en la base de datos"),
            @ApiResponse(responseCode = "500", description = "Error al intentar acceder al servidor")
    })
    @PostMapping(value = "/nuevo", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createHabitacion(@Valid @RequestBody HabitacionDTO habitacionDTO) {
        try {
            Habitacion habitacion = setHabitacionFromDTO(habitacionDTO);
            habitacionService.createHabitacion(habitacion);

            return new ResponseEntity<>("Habitacion creada correctamente", HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("Esta habitación ya esta registrada en la base de datos", HttpStatus.CONFLICT);
        } catch (HotelException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Obtener todas las habitaciones",
            description = "Este método devuelve todas las habitaciones disponibles en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Éxito - Habitaciones encontradas correctamente"),
            @ApiResponse(responseCode = "404", description = "No encontrado - No se encontraron habitaciones"),
            @ApiResponse(responseCode = "500", description = "Error al intentar acceder al servidor")
    })
    @GetMapping()
    public ResponseEntity<?> readHabitacion() {
        try {
            return new ResponseEntity<>(habitacionService.readHabitacion(), HttpStatus.OK);
        } catch (HotelException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Obtener habitaciones por ID de hotel",
            description = "Este método devuelve todas las habitaciones asociadas a un hotel específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Éxito - Habitaciones encontradas correctamente"),
            @ApiResponse(responseCode = "404", description = "No encontrado - No se encontraron habitaciones"),
            @ApiResponse(responseCode = "500", description = "Error al intentar acceder al servidor")
    })
    @GetMapping(value = "/hotel/{id}")
    public ResponseEntity<?> findHabitacionByIdHotel(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(habitacionService.findHabitacionByIdHotel(id), HttpStatus.OK);
        } catch (HotelException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Obtener habitación por ID",
            description = "Este método devuelve una habitación específica según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Éxito - Habitación encontrada correctamente"),
            @ApiResponse(responseCode = "404", description = "No encontrado - La habitación no fue encontrada"),
            @ApiResponse(responseCode = "500", description = "Error al intentar acceder al servidor")
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findHabitacionById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(habitacionService.findHabitacionById(id), HttpStatus.OK);
        } catch (HotelException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Actualizar habitación por ID",
            description = "Este método actualiza una habitación específica según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Éxito - Habitación actualizada correctamente"),
            @ApiResponse(responseCode = "404", description = "No encontrado - La habitación no fue encontrada"),
            @ApiResponse(responseCode = "500", description = "Error al intentar acceder al servidor")
    })
    @PutMapping(value = "/actualizar/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateHotel(@NotNull @PathVariable Long id,
                                         @Valid @RequestBody HabitacionUpdateDTO habitacionUpdateDTO) {
        try {
            Habitacion habitacion = habitacionService.findHabitacionById(id);
            habitacion.setDescripcion(habitacionUpdateDTO.getDescripcion());
            habitacion.setPrecio(habitacionUpdateDTO.getPrecio());
            habitacion.setTipo(habitacionUpdateDTO.getTipo());
            habitacionService.createHabitacion(habitacion);

            return new ResponseEntity<>("Se ha actualizado correctamente", HttpStatus.OK);
        } catch (HotelException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Eliminar habitación por ID",
            description = "Este método elimina una habitación específica según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Éxito - Habitación eliminada correctamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta - Esta habitación ya está eliminada"),
            @ApiResponse(responseCode = "404", description = "No encontrado - La habitación no fue encontrada"),
            @ApiResponse(responseCode = "500", description = "Error al intentar acceder al servidor")
    })
    @DeleteMapping(value = "/eliminar/{id}")
    public ResponseEntity<?> deleteHabitacionById(@NotNull @PathVariable Long id) {
        try {
            Habitacion habitacion = habitacionService.findHabitacionById(id);
            if (habitacion.getFechaBaja() != null) {
                return new ResponseEntity<>("Esta habitación ya esta eliminada", HttpStatus.BAD_REQUEST);
            }
            habitacion.setFechaBaja(LocalDate.now());
            habitacion.setUsuarioBaja(SecurityContextHolder.getContext().getAuthentication().getName());
            habitacionService.createHabitacion(habitacion);

            return new ResponseEntity<>("Se ha borrado correctamente", HttpStatus.OK);
        } catch (HotelException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Este método permite crear una habitación nueva a partir de un DTO introducido
     * como parámetro.
     *
     * @param habitacionDTO
     * @return
     * @throws HotelException
     */
    public Habitacion setHabitacionFromDTO(HabitacionDTO habitacionDTO) throws HotelException {
        Hotel hotel = hotelService.findHotelByCodigo(habitacionDTO.getCodHotel());
        Habitacion habitacion = new Habitacion();
        habitacion.setHotel(hotel);
        habitacion.setCodigo(habitacionDTO.getCodigo());
        habitacion.setTipo(habitacionDTO.getTipo());
        habitacion.setPrecio(habitacionDTO.getPrecio());
        habitacion.setDescripcion(habitacionDTO.getDescripcion());
        habitacion.setFechaAlta(LocalDate.now());
        habitacion.setUsuarioAlta(SecurityContextHolder.getContext().getAuthentication().getName());
        return habitacion;
    }

}
