package dev.doris.sesion18_formato;

import java.math.BigDecimal;

public class Parte1Format {
    public static void main(String[] args) {
        String bolsillo = "Ahorro";
        int movimientos = 3;
        BigDecimal saldo = new BigDecimal("89.75");

        // Con concatenación (como hasta ahora)
        String conMas = "Bolsillo " + bolsillo + ": " + movimientos + " movimientos, saldo " + saldo;
        System.out.println(conMas);

        // Con String.format: un molde con huecos y los valores aparte
        String conFormat = String.format("Bolsillo %s: %d movimientos, saldo %s", bolsillo, movimientos, saldo);
        System.out.println(conFormat);

        System.out.println(conMas.equals(conFormat));

        // Los huecos se llenan en orden: el primer valor va al primer hueco
        System.out.println(String.format("%s - %s", "uno", "dos"));
        System.out.println(String.format("%s - %s", "dos", "uno"));

        // printf: arma el mismo texto y lo imprime de una vez (no devuelve nada)
        System.out.printf("Bolsillo %s: %d movimientos\n", bolsillo, movimientos);
    }
}
