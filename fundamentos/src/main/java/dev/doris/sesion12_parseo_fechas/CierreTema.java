package dev.doris.sesion12_parseo_fechas;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class CierreTema {

    public static void main(String[] args) {
        DateTimeFormatter normal = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter flexible = DateTimeFormatter.ofPattern("d/M/yyyy");
        DateTimeFormatter estricto = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);

        System.out.println("[1] " + LocalDate.parse("2026-12-31"));

        try {
            System.out.println("[2] " + LocalDate.parse("31/12/2026"));
        } catch (DateTimeParseException error) {
            System.out.println("[2] sin formatter");
        }

        System.out.println("[3] " + LocalDate.parse("7/3/2026", flexible));

        try {
            System.out.println("[4] " + LocalDate.parse("7/3/2026", normal));
        } catch (DateTimeParseException error) {
            System.out.println("[4] falla");
        }

        System.out.println("[5] " + LocalDate.parse("29/02/2026", normal));

        System.out.println("[6] " + LocalDate.parse("29/02/2024", estricto));

        try {
            System.out.println("[7] " + LocalDate.parse("29/02/2026", estricto));
        } catch (DateTimeParseException error) {
            System.out.println("[7] invalida");
        }

        try {
            LocalDate fecha = LocalDate.parse("abc", normal);
            System.out.println("[8] " + fecha);
        } catch (DateTimeException error) {
            System.out.println("[8] atrapada");
        }
    }
}
