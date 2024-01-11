package com.hackaboss.pruebatec4.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FechaUtil {

    /**
     * Este método permite convertir una fecha String a LocalDate.
     *
     * @param fechaString
     * @return
     */
    public static LocalDate formatterFecha(String fechaString) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate fecha = null;
        if (!fechaString.isEmpty()) {
            fecha = LocalDate.parse(fechaString, formatter);
        }
        return fecha;
    }
}
