package dev.doris.sesion14_parseo_csv;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class CierreDeTema {

    static BigDecimal monto(String texto) {
        try {
            return new BigDecimal(texto.trim()).setScale(2, RoundingMode.HALF_EVEN);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("monto: " + texto);
        }
    }

    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/uuuu")
                .withResolverStyle(ResolverStyle.STRICT);
        String[] partes = "31/6/2026, 0002 ,CREDITO, 20.5".split(",");

        System.out.println("[1] " + partes.length);
        System.out.println("[2] " + (partes[1].trim() == "0002"));
        System.out.println("[3] " + monto(partes[3]));
        System.out.println("[4] " + monto("0.245"));
        System.out.println("[5] " + new BigDecimal("0.245").setScale(2, RoundingMode.HALF_UP));

        try {
            new BigDecimal("1,50");
            System.out.println("[6] creado");
        } catch (IllegalArgumentException e) {
            System.out.println("[6] atrapada");
        }

        try {
            LocalDate fecha = LocalDate.parse(partes[0], formatter);
            System.out.println("[7] " + fecha);
        } catch (DateTimeParseException e) {
            System.out.println("[7] fecha invalida");
        }

        try {
            System.out.println("[8] " + partes[4]);
        } catch (IllegalArgumentException e) {
            System.out.println("[8] atrapada");
        }
    }
}
