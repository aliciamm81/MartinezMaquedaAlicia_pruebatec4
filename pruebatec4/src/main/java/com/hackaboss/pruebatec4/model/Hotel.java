package com.hackaboss.pruebatec4.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.Valid;
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
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToMany(mappedBy = "hotel")
    @JsonIgnore
    private List<Habitacion> habitaciones;

    @NotNull(message = "Debe de tener codigo de hotel")
    @Column(unique = true)
    private String codigo;

    @NotNull(message = "Debe de rellenar el campo nombre")
    @Pattern(regexp = "[0-9A-Za-zAÑáéióúAEÍO6¡!¿?@$%()=+-€/.,~\s]{1,50}", message = "El nombre debe contener letras, espacios y opcionalmente números.")
    private String nombre;

    @NotNull(message = "Debe de rellenar el campo ciudad")
    @Pattern(regexp = "[0-9A-Za-zAÑáéióúAEÍO6¡!¿?@$%()=+-€/.,~\s]{1,50}", message = "El nombre debe contener solo letras y espacios. ")
    private String ciudad;

    @NotNull(message = "Este campo es obligatorio")
    private String usuarioAlta;

    @NotNull(message = "Este campo es obligatorio")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaAlta;

    private String usuarioBaja;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaBaja;
}
