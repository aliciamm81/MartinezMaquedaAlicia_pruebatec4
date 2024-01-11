package com.hackaboss.pruebatec4.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
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
public class HabitacionDTO {

    @NotNull(message = "Debe de indicar un codigo de hotel asociado")
    @Schema(name = "Product ID", example = "1", required = true)
    private String codHotel;

    @NotNull(message = "Debe de indicar un codigo de habitación")
    private String codigo;

    @NotNull(message = "Debe de indicar el tipo de habitación")
    @Pattern(regexp = "^(doble|triple|individual|múltiple)$", message = "El tipo de habitación tiene que ser entre una de estas opciones: doble, triple, individual, múltiple")
    private String tipo;

    private String descripcion;

    @NotNull(message = "Debe de indicar el precio de la habitación")
    @DecimalMin(value = "0.0", inclusive = false, message = "Debe ser un número double mayor que cero")
    private Double precio;
    
}
