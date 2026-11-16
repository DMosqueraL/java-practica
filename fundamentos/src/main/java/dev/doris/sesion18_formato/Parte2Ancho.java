package dev.doris.sesion18_formato;

import java.math.BigDecimal;

public class Parte2Ancho {
    public static void main(String[] args) {
        // Los corchetes solo están para ver dónde quedan los espacios
        System.out.println(String.format("[%s]", "Ahorro"));
        System.out.println(String.format("[%10s]", "Ahorro"));
        System.out.println(String.format("[%-10s]", "Ahorro"));
        System.out.println(String.format("[%5d]", 42));
        System.out.println(String.format("[%-5d]", 42));

        // Si el valor es más largo que el ancho, no se corta
        System.out.println(String.format("[%3s]", "Ahorro"));

        // Dos columnas: el nombre a la izquierda (8) y el saldo a la derecha (10)
        System.out.println(String.format("%-8s%10s", "Ahorro", new BigDecimal("89.75")));
        System.out.println(String.format("%-8s%10s", "Viajes", new BigDecimal("1250.00")));
        System.out.println(String.format("%-8s%10s", "Ocio", new BigDecimal("-5.50")));
    }
}
