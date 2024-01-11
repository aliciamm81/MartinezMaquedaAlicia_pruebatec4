package com.hackaboss.pruebatec4.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Valid
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToMany(mappedBy = "cliente")
    @JsonIgnore
    private List<ReservaHotel> reservasHotel;

    @OneToMany(mappedBy = "cliente")
    @JsonIgnore
    private List<ReservaVuelo> reservasVuelo;

    @NotNull(message = "Debe rellenar el campo nombre")
    @Pattern(regexp = "^[A-Za-záéíóúüñÁÉÍÓÚÜÑ]+([\\s'\\-][A-Za-záéíóúüñÁÉÍÓÚÜÑ]+)*$", message = "Nombre no válido")
    private String nombre;

    @NotNull(message = "Debe rellenar el campo apellido")
    @Pattern(regexp = "^[A-Za-záéíóúüñÁÉÍÓÚÜÑ]+([\\s'\\-][A-Za-záéíóúüñÁÉÍÓÚÜÑ]+)*$", message = "Apellido no válido")
    private String apellido;

    @NotNull(message = "Debe rellenar el campo dni")
    @Pattern(regexp = "^[0-9]{8}[A-Za-z]$", message = "DNI no válido")
    private String dni;

    @NotNull(message = "Debe rellenar el campo email")
    @Email(message = "Email no válido")
    private String email;

    @NotNull(message = "Debe rellenar el campo telefono")
    @Pattern(regexp = "^[0-9]{7,15}$", message = "Número de teléfono no válido, tiene que tener como mínimo 7 dígitos y como máximo 15")
    private String telefono;

    @NotNull(message = "Debe rellenar el campo numero de tarjeta")
    @Pattern(regexp = "^[0-9]{15,16}$", message = "Número de tarjeta no válido, como mínimo debe contener 15 dígitos y como máximo 16")
    private String numTarjeta;
}
