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
public class ReservaHotel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_HABITACION")
    @NotNull(message = "Debe de seleccionar una habitación")
    private Habitacion habitacion;

    @ManyToOne
    @JoinColumn(name = "ID_CLIENTE")
    @NotNull(message = "Debe de seleccionar un cliente")
    private Cliente cliente;

    @NotNull(message = "Debe de insertar un nomero de personas")
    @Min(value = 1, message = "El número de personas debe ser mayor o igual a 1")
    @Max(value = 6, message = "El número de personas no debe ser mayor a 6")
    private Integer numPersonas;

    @NotNull(message = "Debe de insertar una fecha de inicio")
    @Future(message = "La fecha tiene que ser superior al día de hoy")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaDesde;

    @NotNull(message = "Debe de insertar una fecha de fin")
    @Future(message = "La fecha tiene que ser superior al día de hoy")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaHasta;

    @NotNull(message = "Debe de insertar un número de noches")
    @Min(value = 1, message = "El número de noches debe ser mayor o igual a 1")
    @Max(value = 60, message = "El número de noches no debe ser mayor a 60")
    private Integer noches;

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
