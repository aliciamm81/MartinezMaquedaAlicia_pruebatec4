package com.hackaboss.pruebatec4.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Valid
public class Habitacion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true)
    private String codigo;

    @NotNull(message = "Debe de tener un hotel asociado")
    @ManyToOne
    @JoinColumn(name = "ID_HOTEL")
    private Hotel hotel;

    @OneToMany(mappedBy = "habitacion")
    @JsonIgnore
    private List<ReservaHotel> reservasHotel;

    @NotNull(message = "Debe indicar un tipo de habitación")
    @Pattern(regexp = "^(doble|triple|individual|múltiple)$", message = "El tipo de habitación tiene que ser entre una de estas opciones: doble, triple, individual, múltiple")
    private String tipo;

    private String descripcion;

    @NotNull(message = "Debe de rellenar el campo de precio ")
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
