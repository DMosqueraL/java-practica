package dev.doris.sesion11_java_time;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Vencimientos {
    static boolean estaVencida(LocalDate vencimiento, LocalDate hoy) {
        return hoy.isAfter(vencimiento);
    }

    static LocalDate masReciente(LocalDate a, LocalDate b) {
        if (b.compareTo(a) > 0) {
            return b;
        }
        return a;
    }

    // Ambos extremos incluidos
    static boolean enRango(LocalDate fecha, LocalDate desde, LocalDate hasta) {
        return !fecha.isBefore(desde) && !fecha.isAfter(hasta);
    }

    // Ambos extremos excluidos
    static boolean enRangoEstricto(LocalDate fecha, LocalDate desde, LocalDate hasta) {
        return fecha.isAfter(desde) && fecha.isBefore(hasta);
    }

    public static void main(String[] args) {
        LocalDate venc = LocalDate.of(2026, 10, 31);
        LocalDate inicio = LocalDate.of(2026, 11, 2);
        LocalDate fin = LocalDate.of(2026, 11, 6);
        LocalDate copia = LocalDate.of(2026, 11, 2);
        System.out.println(estaVencida(venc, LocalDate.of(2026, 10, 30))); // Salida: false
        System.out.println(estaVencida(venc, venc)); // Salida: false
        System.out.println(estaVencida(venc, venc.plusDays(1))); // Salida: true
        System.out.println(masReciente(venc, LocalDate.of(2026, 10, 30))); // Salida: venc 2026-10-31
        System.out.println(masReciente(venc, venc).equals(venc)); // Salida: true
        System.out.println("[4] " + enRango(fin, inicio, fin));
        System.out.println("[5] " + enRangoEstricto(fin, inicio, fin));
        try {
            LocalDate.of(2026, 4, 31);
            System.out.println("Creada");
        } catch (DateTimeException e) {
            System.out.println("Fecha invalida"); // Salida: Fecha invalida
        }
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
