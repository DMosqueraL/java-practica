package dev.doris.sesion08_bigdecimal;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TotalesPorCuenta {
    public static void main(String[] args) {
        Abono abono1 = new Abono("0001", new BigDecimal("10.50"));
        Abono abono2 = new Abono("0002", new BigDecimal("3.25"));
        Abono abono3 = new Abono("0001", new BigDecimal("0.10"));
        Abono abono4 = new Abono("0003", new BigDecimal("100"));
        Abono abono5 = new Abono("0002", new BigDecimal("2.75"));
        Abono abono6 = new Abono("0001", new BigDecimal("0.20"));

        List<Abono> listaAbono = List.of(abono1, abono2, abono3, abono4, abono5, abono6);

        Map<String, BigDecimal> totales = new HashMap<>();

        for (Abono ab : listaAbono) {
            totales.put(ab.cuenta(), totales.getOrDefault(ab.cuenta(), BigDecimal.ZERO).add(ab.monto()));
        }

        System.out.println();

        // Predicción:
        // Cuenta 0001, Total = $10.80
        // Cuenta 0002, Total = $6.00
        // Cuenta 0003, Total = $100
        for (String cuenta : totales.keySet()) {
            System.out.println("Cuenta " + cuenta + ", Total = $" + totales.get(cuenta));
        }
        System.out.println();

        // Predicción:
        // Cuenta 0004, Total = $0
        System.out.println("Cuenta 0004, Total = $" + totales.getOrDefault("0004", BigDecimal.ZERO));

        System.out.println();

        // Predicción: 0
        System.out.println("0002.compareTo(new BigDecimal(\"6\")) = " + totales.get("0002").compareTo(new BigDecimal("6")));

        // Predicción: false
        System.out.println("0002.equals(new BigDecimal(\"6\")) = " + totales.get("0002").equals(new BigDecimal("6")));

        System.out.println();

        Map<String, BigDecimal> vacio = new HashMap<>();

        //Predicción: Debe lanzar la excepción de NullPointerException pq el get devuelve un null y se quiere convertir a BigDecimal con el add
        System.out.println("Última instrucción = " + vacio.get("0001").add(new BigDecimal("1")));
    }
}
