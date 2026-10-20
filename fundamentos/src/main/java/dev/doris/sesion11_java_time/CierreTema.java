package dev.doris.sesion11_java_time;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class CierreTema {

    // Ambos extremos incluidos
    static boolean enRango(LocalDate fecha, LocalDate desde, LocalDate hasta) {
        return !fecha.isBefore(desde) && !fecha.isAfter(hasta);
    }

    // Ambos extremos excluidos
    static boolean enRangoEstricto(LocalDate fecha, LocalDate desde, LocalDate hasta) {
        return fecha.isAfter(desde) && fecha.isBefore(hasta);
    }

    public static void main(String[] args) {
        LocalDate inicio = LocalDate.of(2026, 11, 2);
        LocalDate fin = LocalDate.of(2026, 11, 6);
        LocalDate copia = LocalDate.of(2026, 11, 2);

        inicio.plusDays(1);
        System.out.println("[1] " + inicio);
        System.out.println("[2] " + (inicio == copia));
        System.out.println("[3] " + inicio.equals(copia));
        System.out.println("[4] " + enRango(fin, inicio, fin));
        System.out.println("[5] " + enRangoEstricto(fin, inicio, fin));

        LocalDateTime cobro = LocalDateTime.of(2026, 11, 6, 9, 5);
        System.out.println("[6] " + cobro);

        try {
            LocalDate.of(2026, 11, 31);
            System.out.println("[7] creada");
        } catch (DateTimeException e) {
            System.out.println("[8] invalida");
        }
    }
}
