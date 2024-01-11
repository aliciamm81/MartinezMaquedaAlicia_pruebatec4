package com.hackaboss.pruebatec4.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
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
@Entity
@Valid
public class ReservaVuelo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull(message = "Debe de insertar un nombre de vuelo")
    @ManyToOne
    @JoinColumn(name = "ID_VUELO")
    private Vuelo vuelo;

    @NotNull(message = "Debe de indicar el numero de personas")
    @Min(value = 1, message = "El número de asientos debe ser debe ser mayor o igual a 1")
    @Max(value = 10, message = "No se pueden reservar más de 10 asientos ")
    private Integer numPersonas;

    @NotNull(message = "Debe de indicar el tipo de asiento")
    @Pattern(regexp = "^(economico|premium)$", message = "El tipo de asientos tiene que ser entre una de estas opciones: económico o premium")
    private String tipoAsiento;

    @NotNull(message = "Debe de seleccionar un cliente")
    @ManyToOne
    @JoinColumn(name = "ID_CLIENTE")
    private Cliente cliente;

    @DecimalMin(value = "0.0", inclusive = false, message = "Debe ser un número double mayor que cero")
    private Double precio;

    @NotNull(message = "Este campo es obligatorio")
    private String usuarioAlta;

    @NotNull(message = "Este campo es obligatorio")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaAlta;

    private String usuarioBaja;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaBaja;
}
