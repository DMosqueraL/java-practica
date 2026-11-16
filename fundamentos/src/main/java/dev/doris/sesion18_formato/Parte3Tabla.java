package dev.doris.sesion18_formato;

import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

public class Parte3Tabla {

    static String tabla(Map<String, BigDecimal> saldos) {
        String texto = String.format("%-8s%10s", "Bolsillo", "Saldo");
        for (String bolsillo : saldos.keySet()) {
            BigDecimal saldo = saldos.get(bolsillo).setScale(2);
            texto = texto + "\n" + String.format("%-8s%10s", bolsillo, saldo);
        }
        texto = texto + "\n" + String.format("Bolsillos: %d", saldos.size());
        return texto;
    }

    public static void main(String[] args) {
        // Un monto en cero, sin y con setScale(2)
        System.out.println(String.format("%-8s%10s", "Ocio", BigDecimal.ZERO));
        System.out.println(String.format("%-8s%10s", "Ocio", BigDecimal.ZERO.setScale(2)));
        System.out.println();

        Map<String, BigDecimal> saldos = new TreeMap<>();
        saldos.put("Viajes", new BigDecimal("1250.00"));
        saldos.put("Ahorro", new BigDecimal("89.75"));
        saldos.put("Ocio", BigDecimal.ZERO);

        System.out.println(tabla(saldos));
    }
}
