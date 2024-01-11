package com.hackaboss.pruebatec4.controller;

import com.hackaboss.pruebatec4.dto.HotelDTO;
import com.hackaboss.pruebatec4.exception.HotelException;
import com.hackaboss.pruebatec4.model.Hotel;
import com.hackaboss.pruebatec4.service.IHotelService;
import com.hackaboss.pruebatec4.util.FechaUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Validated
@RestController
@RequestMapping("/agencia/hoteles")
public class HotelController {

    @Autowired
    IHotelService hotelService;

    @Operation(summary = "Crear un hotel nuevo",
            description = "Este método permite crear un nuevo hotel en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Éxito - Hotel creado correctamente"),
            @ApiResponse(responseCode = "409", description = "Conflicto - Este hotel ya existe en la base de datos"),
            @ApiResponse(responseCode = "500", description = "Error al intentar acceder al servidor")

    })
    @PostMapping(value = "/nuevo", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createHotel(@Valid @RequestBody HotelDTO hotelDTO) {
        try {
            Hotel hotel = new Hotel();
            hotel.setCodigo(hotelDTO.getCodigo());
            hotel.setNombre(hotelDTO.getNombre());
            hotel.setCiudad(hotelDTO.getCiudad());
            hotel.setUsuarioAlta(SecurityContextHolder.getContext().getAuthentication().getName());
            hotel.setFechaAlta(LocalDate.now());
            hotelService.createHotel(hotel);

            return new ResponseEntity<>("Hotel creado correctamente", HttpStatus.OK);

        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("Este hotel ya existe en la base de datos", HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "Actualizar un hotel por ID",
            description = "Este método permite actualizar un hotel existente en la base de datos por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Éxito - Hotel actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta - Este hotel ya está eliminado"),
            @ApiResponse(responseCode = "404", description = "No encontrado - El hotel no fue encontrada"),
            @ApiResponse(responseCode = "500", description = "Error al intentar acceder al servidor")
    })
    @PutMapping(value = "/actualizar/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateHotel(@PathVariable Long id, @Valid @RequestBody HotelDTO hotelDTO) {
        try {
            Hotel hotel = hotelService.findHotelById(id);
            if (hotel.getFechaBaja() != null) {
                return new ResponseEntity<>("El hotel seleccionado no se puede actualizar ya que está dado de baja", HttpStatus.BAD_REQUEST);
            }
            hotel.setNombre(hotelDTO.getNombre());
            hotel.setCiudad(hotelDTO.getCiudad());
            hotel.setCodigo(hotelDTO.getCodigo());
            hotelService.createHotel(hotel);

            return new ResponseEntity<>("Hotel actualizado correctamente", HttpStatus.OK);

        } catch (HotelException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Eliminar un hotel por ID",
            description = "Este método permite eliminar un hotel de la base de datos por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Éxito - Borrado correcto"),
            @ApiResponse(responseCode = "404", description = "No encontrado - No existe ningún registro con este ID"),
            @ApiResponse(responseCode = "409", description = "Conflicto - No se puede borrar este hotel ya que tiene reservas pendientes"),
            @ApiResponse(responseCode = "500", description = "Error al intentar acceder al servidor")
    })
    @DeleteMapping(value = "/eliminar/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteHotel(@PathVariable Long id) {
        try {
            Hotel hotel = hotelService.findHotelById(id);
            if (hotel.getFechaBaja() != null) {
                return new ResponseEntity<>("El hotel seleccionado ya se encuentra dado de baja", HttpStatus.CONFLICT);
            }
            if (hotelService.existsReservasAfterByHotel(id, LocalDate.now())) {
                return new ResponseEntity<>("No se puede borrar este hotel ya que tiene reservas pendientes", HttpStatus.CONFLICT);
            }
            hotel.setFechaBaja(LocalDate.now());
            hotel.setUsuarioBaja(SecurityContextHolder.getContext().getAuthentication().getName());
            hotelService.createHotel(hotel);

            return new ResponseEntity<>("Borrado correcto", HttpStatus.OK);
        } catch (HotelException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Obtener un hotel por su ID",
            description = "Este método permite buscar un hotel por su ID en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Éxito - Se encontró el hotel"),
            @ApiResponse(responseCode = "404", description = "No encontrado - No existe ningún registro con este ID"),
            @ApiResponse(responseCode = "500", description = "Error al intentar acceder al servidor")
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findHotelById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(hotelService.findHotelById(id), HttpStatus.OK);
        } catch (HotelException e) {
            return new ResponseEntity<>("No existe ningun registro con este id", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Obtener todos los hoteles",
            description = "Este método permite obtener todos los hoteles registrados en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Éxito - Se obtuvieron los hoteles"),
            @ApiResponse(responseCode = "404", description = "No encontrado - No existe ningún registro cen la base de datos"),
            @ApiResponse(responseCode = "500", description = "Error al intentar acceder al servidor")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> readHoteles() {
        try {
            return new ResponseEntity<>(hotelService.readHoteles(), HttpStatus.OK);
        } catch (HotelException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Obtener hoteles disponibles por fechas y destino",
            description = "Este método permite buscar hoteles disponibles para reservar según fechas y destino")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Éxito - Hoteles disponibles encontrados"),
            @ApiResponse(responseCode = "404", description = "No encontrado - No existe ningún registro con este ID"),
            @ApiResponse(responseCode = "500", description = "Error al intentar acceder al servidor")
    })
    @GetMapping(value = "/buscar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findHotelesDisponiblesByFechaYCiudad(@RequestParam String fechaDesde,
                                                                  @RequestParam String fechaHasta,
                                                                  @RequestParam String destino) {
        try {
            List<Hotel> hotelesDisponibles = hotelService.findHotelesDisponiblesByFechaYCiudad(FechaUtil.formatterFecha(fechaDesde), FechaUtil.formatterFecha(fechaHasta), destino);
            return new ResponseEntity<>(hotelesDisponibles, HttpStatus.OK);
        } catch (HotelException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
