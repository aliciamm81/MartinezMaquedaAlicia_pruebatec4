package com.hackaboss.pruebatec4.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
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
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "UnicoVueloDestinoFecha", columnNames = {"numVuelo", "origen", "destino", "fecha"}),
})
public class Vuelo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull(message = "Debe de insertar un numero de vuelo")
    private String numVuelo;

    @OneToMany(mappedBy = "vuelo")
    @JsonIgnore
    private List<ReservaVuelo> reservas;

    @NotNull(message = "Debe de indicar el origen")
    @Pattern(regexp = "[0-9A-Za-zAÑáéióúAEÍO6¡!¿?@$%()=+-€/.,~\s]{1,50}", message = "El nombre debe contener letras, espacios y opcionalmente números.")
    private String origen;

    @NotNull(message = "Debe de indicar el destino")
    @Pattern(regexp = "[0-9A-Za-zAÑáéióúAEÍO6¡!¿?@$%()=+-€/.,~\s]{1,50}", message = "El nombre debe contener letras, espacios y opcionalmente números.")
    private String destino;

    @NotNull(message = "Debe de indicar la fecha")
    @Future(message = "La fecha tiene que ser superior al día de hoy")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fecha;

    private Integer asientosEconomicos;

    private Double precioAsientoEconomico;

    private Integer asientosPremium;

    private Double precioAsientoPremium;

    @Min(value = 0, message = "No hay suficientes asientos disponibles")
    private Integer asientosEconomicosDisponibles;

    @Min(value = 0, message = "No hay suficientes asientos disponibles")
    private Integer asientosPremiumDisponibles;

    @NotNull(message = "Este campo es obligatorio")
    private String usuarioAlta;

    @NotNull(message = "Este campo es obligatorio")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaAlta;

    private String usuarioBaja;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaBaja;
}
