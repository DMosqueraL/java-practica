package dev.doris.sesion11_java_time;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Transacciones {
    static List<Transaccion> filtrarPorRango(List<Transaccion> transacciones, LocalDate desde, LocalDate hasta) {
        if (transacciones == null) {
            throw new IllegalArgumentException("La lista no puede ser nula.");
        }
        if (desde == null || hasta == null) {
            throw new IllegalArgumentException("Las fechas no pueden ser nulas.");
        }
        if (desde.isAfter(hasta)) {
            throw new IllegalArgumentException("El rango inferior de las fechas no puede ser mayor al rango superior.");
        }

        List<Transaccion> transaccion = new ArrayList<>();
        for (Transaccion trx : transacciones) {
            if (!trx.fecha().isBefore(desde) && !trx.fecha().isAfter(hasta)) {
                transaccion.add(trx);
            }
        }
        return transaccion;
    }

    public static void main(String[] args) {
        Transaccion trx1 = new Transaccion("0001", new BigDecimal("100.50"), LocalDate.of(2026, 10, 5));
        Transaccion trx2 = new Transaccion("0002", new BigDecimal("40.00"), LocalDate.of(2026, 10, 12));
        Transaccion trx3 = new Transaccion("0001", new BigDecimal("250.00"), LocalDate.of(2026, 10, 12));
        Transaccion trx4 = new Transaccion("0003", new BigDecimal("15.75"), LocalDate.of(2026, 10, 19));
        Transaccion trx5 = new Transaccion("0002", new BigDecimal("600.00"), LocalDate.of(2026, 10, 1));
        Transaccion trx6 = new Transaccion("0001", new BigDecimal("80.25"), LocalDate.of(2026, 10, 23));
        Transaccion trx7 = new Transaccion("0003", new BigDecimal("120.00"), LocalDate.of(2026, 10, 5));
        Transaccion trx8 = new Transaccion("0002", new BigDecimal("33.10"), LocalDate.of(2026, 11, 2));

        List<Transaccion> transacciones = List.of(trx1, trx2, trx3, trx4, trx5, trx6, trx7, trx8);

        try {
            System.out.println("[1]" + filtrarPorRango(transacciones, LocalDate.of(2026, 11, 5), LocalDate.of(2026, 10, 5)));
        } catch (IllegalArgumentException error) {
            System.out.println(error.getMessage());
        }
        try {
            System.out.println("[1]" + filtrarPorRango(transacciones, null, LocalDate.of(2026, 10, 5)));
        } catch (IllegalArgumentException error) {
            System.out.println(error.getMessage());
        }
        System.out.println("[1]" + filtrarPorRango(transacciones, LocalDate.of(2026, 10, 5), LocalDate.of(2026, 10, 15)));
    }
}
