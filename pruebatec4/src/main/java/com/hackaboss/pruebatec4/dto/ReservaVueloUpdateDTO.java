package com.hackaboss.pruebatec4.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Valid
public class ReservaVueloUpdateDTO {

    @NotNull(message = "Debe de indicar el numero de personas de la reserva")
    @Min(value = 1, message = "El número de asientos debe ser debe ser mayor o igual a 1")
    @Max(value = 10, message = "No se pueden reservar más de 10 asientos ")
    private Integer numPersonas;

    @NotNull(message = "Debe de indicar el tipo de asiento")
    @Pattern(regexp = "^(economico|premium)$", message = "El tipo de asientos tiene que ser entre una de estas opciones: económico o premium")
    private String tipoAsiento;

}
