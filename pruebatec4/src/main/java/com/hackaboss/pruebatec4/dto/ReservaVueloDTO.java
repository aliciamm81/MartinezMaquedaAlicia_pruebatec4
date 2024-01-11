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
public class ReservaVueloDTO {

    @NotNull(message = "Debe de indicar el numero de personas de la reserva")
    @Min(value = 1, message = "El número de asientos debe ser debe ser mayor o igual a 1")
    @Max(value = 10, message = "No se pueden reservar más de 10 asientos ")
    private Integer numPersonas;

    @NotNull(message = "Debe de indicar el tipo de asiento")
    @Pattern(regexp = "^(economico|premium)$", message = "El tipo de asientos tiene que ser entre una de estas opciones: económico o premium")
    private String tipoAsiento;

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
    @Pattern(regexp = "^[0-9]{7,15}$", message = "Número de teléfono no válido")
    private String telefono;

    @NotNull(message = "Debe de indicar el número de tarjeta del cliente")
    @Pattern(regexp = "^[0-9]{15,16}$", message = "Número de tarjeta no válido, como mínimo debe contener 15 dígitos y como máximo 16")
    private String numTarjeta;
}
