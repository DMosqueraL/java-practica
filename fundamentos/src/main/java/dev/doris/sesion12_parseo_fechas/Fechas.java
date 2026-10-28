package dev.doris.sesion12_parseo_fechas;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class Fechas {
    static LocalDate parsear(String texto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/uuuu").withResolverStyle(ResolverStyle.STRICT);

        if (texto == null) {
            throw new IllegalArgumentException("El texto no puede ser nulo.");
        }
        try {
            return LocalDate.parse(texto, formatter);
        } catch (DateTimeParseException error) {
            throw new IllegalArgumentException("Fecha inválida: " + texto);
        }
    }
}
