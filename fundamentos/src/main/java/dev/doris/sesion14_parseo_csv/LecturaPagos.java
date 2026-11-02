package dev.doris.sesion14_parseo_csv;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class LecturaPagos {
    static BigDecimal montoDePago(String linea) {
        String[] partes = linea.split(",");
        if (partes.length != 2) {
            throw new IllegalArgumentException("Columnas: " + partes.length);
        }
        try {
            return new BigDecimal(partes[1].trim()).setScale(2, RoundingMode.HALF_EVEN);
        } catch (NumberFormatException error) {
            throw new IllegalArgumentException("Monto invalido: " + partes[1].trim());
        }
    }

    static boolean esDeCuenta(String linea, String cuenta) {
        String[] partes = linea.split(",");
        return partes[0].trim().equals(cuenta);
    }

    public static void main(String[] args) {

        // Salida: 10.12
        System.out.println("[1] " + montoDePago("0001,10.125"));

        // Salida: 7.50
        System.out.println("[2] " + montoDePago(" 0002 , 7.5 "));

        // Salida: 10.14
        System.out.println("[3] " + montoDePago("0003,10.135"));

        // Salida: Columnas: 1
        try {
            System.out.println("[4] " + montoDePago("0004"));
        } catch (IllegalArgumentException error) {
            System.out.println("[4] " + error.getMessage());
        }

        // Salida: Monto invalido: abc
        try {
            System.out.println("[5] " + montoDePago("0005,abc"));
        } catch (IllegalArgumentException error) {
            System.out.println("[5] " + error.getMessage());
        }

        // Salida: true
        System.out.println("[6] " + esDeCuenta("0001,10.125", "0001"));

        // Salida: false
        String[] partes = "0001,10.125".split(",");
        System.out.println("[7] " + (partes[0] == "0001"));
    }
}
