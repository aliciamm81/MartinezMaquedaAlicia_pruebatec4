package com.hackaboss.pruebatec4.dto;

import jakarta.persistence.Column;
import jakarta.validation.Valid;
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
public class HotelDTO {

    @NotNull(message = "Debe de indicar un codigo de hotel")
    @Column(unique = true)
    private String codigo;

    @NotNull(message = "Debe de indicar el nombre del hotel")
    @Pattern(regexp = "[0-9A-Za-zAÑáéióúAEÍO6¡!¿?@$%()=+-€/.,~\s]{1,50}", message = "El nombre debe contener letras, espacios y opcionalmente números.")
    private String nombre;

    @NotNull(message = "Debe de indicar la ciudad del hotel")
    @Pattern(regexp = "[0-9A-Za-zAÑáéióúAEÍO6¡!¿?@$%()=+-€/.,~\s]{1,50}", message = "El nombre debe contener solo letras y espacios. ")
    private String ciudad;
}
