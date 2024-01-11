package com.hackaboss.pruebatec4.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
public class ReservaHotelUpdateDTO {

    @NotNull(message = "Debe de indicar la fecha de inicio de la reserva")
    @Future(message = "La fecha tiene que ser superior al día de hoy")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaDesde;

    @NotNull(message = "Debe de indicar la fecha de fin de la reserva")
    @Future(message = "La fecha tiene que ser superior al día de hoy")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaHasta;

    @NotNull(message = "Debe de indicar el número de personas de la habitación")
    @Min(value = 1, message = "El número de personas debe ser mayor o igual a 1")
    @Max(value = 6, message = "El número de personas no debe ser mayor a 6")
    private Integer numPersonas;

}
