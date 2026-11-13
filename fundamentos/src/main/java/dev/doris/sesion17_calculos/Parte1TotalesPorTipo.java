package dev.doris.sesion17_calculos;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parte1TotalesPorTipo {

    // Versión A: solo acumula (como en la kata-06). Un tipo sin movimientos no entra al Map.
    static Map<TipoMovimiento, BigDecimal> totalesSinIniciar(List<Transaccion> movimientos) {
        Map<TipoMovimiento, BigDecimal> totales = new HashMap<>();
        for (Transaccion movimiento : movimientos) {
            BigDecimal actual = totales.getOrDefault(movimiento.tipo(), BigDecimal.ZERO);
            totales.put(movimiento.tipo(), actual.add(movimiento.monto()));
        }
        return totales;
    }

    public static void main(String[] args) {
        List<Transaccion> soloIngresos = List.of(
                new Transaccion("Ahorro", TipoMovimiento.INGRESO, new BigDecimal("100.50")),
                new Transaccion("Viajes", TipoMovimiento.INGRESO, new BigDecimal("50.25")));

        Map<TipoMovimiento, BigDecimal> versionA = totalesSinIniciar(soloIngresos);
        System.out.println("[A] INGRESO: " + versionA.get(TipoMovimiento.INGRESO));
        System.out.println("[A] GASTO: " + versionA.get(TipoMovimiento.GASTO));
        System.out.println("[A] claves: " + versionA.size());

        Map<TipoMovimiento, BigDecimal> versionB = CalculoBolsillos.totalesPorTipo(soloIngresos);
        System.out.println("[B] INGRESO: " + versionB.get(TipoMovimiento.INGRESO));
        System.out.println("[B] GASTO: " + versionB.get(TipoMovimiento.GASTO));
        System.out.println("[B] claves: " + versionB.size());

        Map<TipoMovimiento, BigDecimal> vacio = CalculoBolsillos.totalesPorTipo(List.of());
        System.out.println("[C] GASTO con lista vacía: " + vacio.get(TipoMovimiento.GASTO));
    }
}
