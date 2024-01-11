package com.hackaboss.pruebatec4.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
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
public class ReservaHotelDTO {

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

    @NotNull(message = "Debe de indicar el nombre del cliente")
    @Pattern(regexp = "^[A-Za-záéíóúüñÁÉÍÓÚÜÑ]+([\\s'\\-][A-Za-záéíóúüñÁÉÍÓÚÜÑ]+)*$", message = "Nombre no válido")
    private String nombre;

    @NotNull(message = "Debe de indicar el apellido del cliente")
    @Pattern(regexp = "^[A-Za-záéíóúüñÁÉÍÓÚÜÑ]+([\\s'\\-][A-Za-záéíóúüñÁÉÍÓÚÜÑ]+)*$", message = "Apellido no válido")
    private String apellido;

    @NotNull(message = "Debe de indicar el DNI del cliente")
    @Pattern(regexp = "^[0-9]{8}[A-Za-z]$", message = "DNI no válido")
    private String dni;

    @NotNull(message = "Debe de indicar el email del cliente")
    @Email(message = "Email no válido")
    private String email;

    @NotNull(message = "Debe de indicar el número de teléfono del cliente")
    @Pattern(regexp = "^[0-9]{7,15}$", message = "Número de teléfono no válido, tiene que tener como mínimo 7 dígitos y como máximo 15")
    private String telefono;

    @NotNull(message = "Debe rellenar el campo numero de tarjeta")
    @Pattern(regexp = "^[0-9]{15,16}$", message = "Número de tarjeta no válido, como mínimo debe contener 15 dígitos y como máximo 16")
    private String numTarjeta;

    private String codigoHabitacion;
}
