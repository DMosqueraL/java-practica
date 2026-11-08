package dev.doris.sesion14_parseo_csv;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class LineaCsv {

    static Transaccion convertir(String linea) {
        String[] partes = linea.split(",", -1);
        if (partes.length != 4) {
            throw new IllegalArgumentException("columnas: " + partes.length);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/uuuu").withResolverStyle(ResolverStyle.STRICT);
        LocalDate fecha;
        try {
            fecha = LocalDate.parse(partes[0].trim(), formatter);
        } catch (DateTimeParseException error) {
            throw new IllegalArgumentException("fecha inválida: " + partes[0].trim());
        }

        String cuenta = partes[1].trim();

        TipoTransaccion tipo;
        try {
            tipo = TipoTransaccion.valueOf(partes[2].trim());
        } catch (IllegalArgumentException error) {
            throw new IllegalArgumentException("tipo inválido: " + partes[2].trim());
        }

        BigDecimal monto;
        try {
            monto = new BigDecimal(partes[3].trim()).setScale(2, RoundingMode.HALF_EVEN);
        } catch (NumberFormatException error) {
            throw new IllegalArgumentException("Monto inválido: " + partes[3].trim());
        }
        return new Transaccion(fecha, cuenta, tipo, monto);
    }

    public static void main(String[] args) {
        String linea1 = "5/6/2026,0001,DEBITO,10.50";
        String linea2 = " 05/06/2026 , 0002 , CREDITO , 80.5 ";
        String linea3 = "7/6/2026,0003,DEBITO,10.125";

        System.out.println(convertir(linea1));
        System.out.println(convertir(linea2));
        System.out.println(convertir(linea3));
    }
}
