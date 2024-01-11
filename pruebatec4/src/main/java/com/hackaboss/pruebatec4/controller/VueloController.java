package com.hackaboss.pruebatec4.controller;

import com.hackaboss.pruebatec4.dto.VueloDTO;
import com.hackaboss.pruebatec4.exception.VueloException;
import com.hackaboss.pruebatec4.model.Vuelo;
import com.hackaboss.pruebatec4.service.IVueloService;
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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/agencia/vuelos")
public class VueloController {
    @Autowired
    IVueloService vueloService;

    @Operation(summary = "Crear un vuelo nuevo",
            description = "Permite crear un nuevo vuelo en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Éxito - Vuelo creado correctamente"),
            @ApiResponse(responseCode = "409", description = "Conflicto - Este Vuelo ya existe en la base de datos"),
            @ApiResponse(responseCode = "500", description = "Error al intentar acceder al servidor")
    })
    @PostMapping(value = "/nuevo", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createVuelo(@Valid @RequestBody VueloDTO vueloDTO) {
        try {
            Vuelo vuelo = createVueloFromDTO(vueloDTO);
            vueloService.create(vuelo);

            return new ResponseEntity<>("Vuelo creado correctamente", HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("No pueden crearse dos vuelos con el mismo código, itinerario y fecha", HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "Actualizar vuelo por ID", description = "Actualiza un vuelo existente por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Éxito - Vuelo actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta - Este vuelo ya está eliminado"),
            @ApiResponse(responseCode = "404", description = "No encontrado - El vuelo no fue encontrada"),
            @ApiResponse(responseCode = "500", description = "Error al intentar acceder al servidor")
    })
    @PutMapping(value = "/actualizar/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateVuelo(@Valid @PathVariable Long id, @Valid @RequestBody VueloDTO vueloDTO) {
        try {
            Vuelo vuelo = vueloService.findVueloById(id);
            if (vuelo.getFechaBaja() != null) {
                return new ResponseEntity<>("No se puede actualizar por encontrarse dado de baja", HttpStatus.BAD_REQUEST);
            }
            vuelo.setDestino(vueloDTO.getDestino());
            vuelo.setFecha(vueloDTO.getFecha());
            vueloService.create(vuelo);
            return new ResponseEntity<>("Vuelo actualiado correctamente", HttpStatus.OK);
        } catch (VueloException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Buscar vuelo por su ID", description = "Obtiene un vuelo por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Éxito - Se encontró el vuelo"),
            @ApiResponse(responseCode = "404", description = "No encontrado - El vuelo no fue encontrada"),
            @ApiResponse(responseCode = "500", description = "Error al intentar acceder al servidor")
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findVueloById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(vueloService.findVueloById(id), HttpStatus.OK);
        } catch (VueloException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Obtener todos los vuelos", description = "Obtiene todos los vuelos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Éxito - Se obtuvieron los vuelos"),
            @ApiResponse(responseCode = "404", description = "No encontrado - No existe ningún registro en la base de datos"),
            @ApiResponse(responseCode = "500", description = "Error al intentar acceder al servidor")})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> readVuelos() {
        try {
            return new ResponseEntity<>(vueloService.readVuelos(), HttpStatus.OK);
        } catch (VueloException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Eliminar vuelo por ID", description = "Elimina un vuelo por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Éxito - Borrado correcto"),
            @ApiResponse(responseCode = "404", description = "No encontrado - No existe ningún registro con este ID"),
            @ApiResponse(responseCode = "409", description = "Conflicto - No se puede borrar este vuelo ya que tiene reservas pendientes"),
            @ApiResponse(responseCode = "500", description = "Error al intentar acceder al servidor")
    })
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> deleteVuelo(@Valid @PathVariable Long id) {
        try {
            Vuelo vuelo = vueloService.findVueloById(id);
            if (vuelo.getFechaBaja() != null) {
                return new ResponseEntity<>("El vuelo seleccionado ya se encuentra dado de baja", HttpStatus.BAD_REQUEST);
            }
            if (vueloService.existsReservasPosterioresParaVuelo(id, LocalDate.now())) {
                return new ResponseEntity<>("No se puede borrar este vuelo ya que tiene reservas pendientes", HttpStatus.CONFLICT);
            }
            vuelo.setFechaBaja(LocalDate.now());
            vuelo.setUsuarioBaja(SecurityContextHolder.getContext().getAuthentication().getName());
            vueloService.create(vuelo);

            return new ResponseEntity<>("Borrado correcto", HttpStatus.OK);
        } catch (VueloException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Buscar vuelos disponibles", description = "Obtiene vuelos disponibles entre dos fechas y destinos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Éxito - Vuelos disponibles encontrados"),
            @ApiResponse(responseCode = "404", description = "No encontrado - No existe ningún registro con estos criterios"),
            @ApiResponse(responseCode = "500", description = "Error al intentar acceder al servidor")
    })
    @GetMapping("/buscar")
    public ResponseEntity<?> findVuelosDisponibles(@RequestParam String fechaDesde,
                                                   @RequestParam String fechaHasta,
                                                   @RequestParam String origen,
                                                   @RequestParam String destino) {
        try {
            List<Vuelo> vuelos = vueloService.findVuelosDisponibles(FechaUtil.formatterFecha(fechaDesde), FechaUtil.formatterFecha(fechaHasta), origen, destino);
            return new ResponseEntity<>(vuelos, HttpStatus.OK);
        } catch (VueloException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Este método permite setear los atributos de un objeto Vuelo en base a uno DTO pasado como parámetro.
     *
     * @param vueloDTO
     * @return
     */
    private Vuelo createVueloFromDTO(VueloDTO vueloDTO) {
        Vuelo vuelo = new Vuelo();
        vuelo.setNumVuelo(vueloDTO.getNumVuelo());
        vuelo.setFecha(vueloDTO.getFecha());
        vuelo.setOrigen(vueloDTO.getOrigen());
        vuelo.setDestino(vueloDTO.getDestino());
        vuelo.setUsuarioAlta(SecurityContextHolder.getContext().getAuthentication().getName());
        vuelo.setFechaAlta(LocalDate.now());
        vuelo.setAsientosPremium(vueloDTO.getAsientosPremium());
        vuelo.setAsientosEconomicos(vueloDTO.getAsientosEconomicos());
        vuelo.setAsientosPremiumDisponibles(vueloDTO.getAsientosPremium());
        vuelo.setAsientosEconomicosDisponibles(vueloDTO.getAsientosEconomicos());
        vuelo.setPrecioAsientoEconomico(vueloDTO.getPrecioAsientoEconomico());
        vuelo.setPrecioAsientoPremium(vueloDTO.getPrecioAsientoPremium());
        return vuelo;
    }

}
