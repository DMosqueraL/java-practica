package dev.doris.sesion12_parseo_fechas;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class EjemploParseoFechas {

    public static void main(String[] args) {
        // [1] Sin formatter: parse solo entiende año-mes-día con ceros (formato ISO)
        System.out.println("[1] " + LocalDate.parse("2026-10-05"));

        // [2] El mismo día escrito como día/mes/año: parse sin formatter no lo entiende
        try {
            System.out.println("[2] " + LocalDate.parse("05/10/2026"));
        } catch (DateTimeParseException error) {
            System.out.println("[2] " + error.getMessage());
        }

        // [3] Con un formatter que describe el formato del texto
        DateTimeFormatter diaMesAnio = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("[3] " + LocalDate.parse("05/10/2026", diaMesAnio));

        // [4] dd exige dos dígitos
        try {
            System.out.println("[4] " + LocalDate.parse("5/10/2026", diaMesAnio));
        } catch (DateTimeParseException error) {
            System.out.println("[4] " + error.getMessage());
        }

        // [5] d y M aceptan uno o dos dígitos
        DateTimeFormatter flexible = DateTimeFormatter.ofPattern("d/M/yyyy");
        System.out.println("[5] " + LocalDate.parse("5/10/2026", flexible) + " y " + LocalDate.parse("05/10/2026", flexible));

        // [6] Fecha que no existe, con el formatter normal
        System.out.println("[6] " + LocalDate.parse("31/02/2026", diaMesAnio));

        // [7] La misma fecha, con un formatter estricto
        DateTimeFormatter estricto = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
        try {
            System.out.println("[7] " + LocalDate.parse("31/02/2026", estricto));
        } catch (DateTimeParseException error) {
            System.out.println("[7] " + error.getMessage());
        }
    }
}
