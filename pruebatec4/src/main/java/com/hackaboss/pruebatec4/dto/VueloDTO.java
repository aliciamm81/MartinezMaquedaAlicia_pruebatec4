package com.hackaboss.pruebatec4.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Valid
public class VueloDTO {

    @NotNull(message = "Debe de insertar un numero de vuelo")
    private String numVuelo;

    @NotNull(message = "Debe de indicar el origen del vuelo")
    @Pattern(regexp = "[0-9A-Za-zAÑáéióúAEÍO6¡!¿?@$%()=+-€/.,~\s]{1,50}", message = "El nombre debe contener letras, espacios y opcionalmente números.")
    private String origen;

    @NotNull(message = "Debe de indicar el destino del vuelo")
    @Pattern(regexp = "[0-9A-Za-zAÑáéióúAEÍO6¡!¿?@$%()=+-€/.,~\s]{1,50}", message = "El nombre debe contener letras, espacios y opcionalmente números.")
    private String destino;

    @NotNull(message = "Debe de indicar la fecha del vuelo")
    @Future(message = "La fecha tiene que ser superior al día de hoy")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fecha;

    private Integer asientosEconomicos;
    private Double precioAsientoEconomico;
    private Integer asientosPremium;
    private Double precioAsientoPremium;

    @Min(value = 0, message = "No hay suficientes asientos disponibles")
    private Integer asientosEconomicosDisponibles;

    @Min(value = 0, message = "No hay suficientes asientos disponibles")
    private Integer asientosPremiumDisponibles;
}
