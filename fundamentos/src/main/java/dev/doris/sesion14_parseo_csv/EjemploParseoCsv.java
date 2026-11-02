package dev.doris.sesion14_parseo_csv;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class EjemploParseoCsv {

    public static void main(String[] args) {
        String linea = "5/6/2026, 0001 , DEBITO ,10.125";

        // [1] a [3] split: corta el texto en cada coma y devuelve un String[]
        String[] partes = linea.split(",");
        System.out.println("[1] " + partes.length);
        System.out.println("[2] [" + partes[1] + "]");
        System.out.println("[3] [" + partes[1].trim() + "]");

        // [4] un índice que no existe
        try {
            System.out.println(partes[4]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("[4] " + e.getMessage());
        }

        // [5] y [6] el texto que sale de split no es literal
        String tipoTexto = partes[2].trim();
        System.out.println("[5] " + (tipoTexto == "DEBITO"));
        System.out.println("[6] " + tipoTexto.equals("DEBITO"));

        // [7] y [8] valueOf con y sin trim
        try {
            TipoTransaccion.valueOf(partes[2]);
        } catch (IllegalArgumentException e) {
            System.out.println("[7] " + e.getMessage());
        }
        System.out.println("[8] " + TipoTransaccion.valueOf(tipoTexto));

        // [9] y [10] escala: cuántos decimales tiene el BigDecimal
        BigDecimal monto = new BigDecimal(partes[3].trim());
        System.out.println("[9] " + monto.scale());
        System.out.println("[10] " + new BigDecimal("10.5").setScale(2));

        // [11] setScale(2) sin RoundingMode cuando sobran decimales
        try {
            monto.setScale(2);
        } catch (ArithmeticException e) {
            System.out.println("[11] " + e.getMessage());
        }

        // [12] a [15] setScale con RoundingMode
        System.out.println("[12] " + monto.setScale(2, RoundingMode.HALF_UP));
        System.out.println("[13] " + monto.setScale(2, RoundingMode.HALF_EVEN));
        System.out.println("[14] " + new BigDecimal("10.135").setScale(2, RoundingMode.HALF_EVEN));
        System.out.println("[15] " + new BigDecimal("10.126").setScale(2, RoundingMode.HALF_EVEN));

        // [16] monto no numérico
        try {
            new BigDecimal("abc");
        } catch (IllegalArgumentException e) {
            System.out.println("[16] " + e);
        }
    }
}
