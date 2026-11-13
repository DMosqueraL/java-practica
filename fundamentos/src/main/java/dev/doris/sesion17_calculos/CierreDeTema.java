package dev.doris.sesion17_calculos;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CierreDeTema {

    static Map<TipoMovimiento, BigDecimal> totalesSinIniciar(List<Transaccion> movimientos) {
        Map<TipoMovimiento, BigDecimal> totales = new HashMap<>();
        for (Transaccion movimiento : movimientos) {
            BigDecimal actual = totales.getOrDefault(movimiento.tipo(), BigDecimal.ZERO);
            totales.put(movimiento.tipo(), actual.add(movimiento.monto()));
        }
        return totales;
    }

    public static void main(String[] args) {
        List<Transaccion> movimientos = List.of(
                new Transaccion("Ahorro", TipoMovimiento.GASTO, new BigDecimal("40.00")),
                new Transaccion("Viajes", TipoMovimiento.INGRESO, new BigDecimal("25.50")),
                new Transaccion("Ahorro", TipoMovimiento.INGRESO, new BigDecimal("10.25")),
                new Transaccion("Viajes", TipoMovimiento.GASTO, new BigDecimal("5.50")));

        List<Transaccion> soloGastos = List.of(
                new Transaccion("Mercado", TipoMovimiento.GASTO, new BigDecimal("12.00")));

        Map<TipoMovimiento, BigDecimal> totales = CalculoBolsillos.totalesPorTipo(movimientos);
        System.out.println("[1] " + totales.get(TipoMovimiento.INGRESO));
        System.out.println("[2] " + totales.get(TipoMovimiento.GASTO));

        Map<TipoMovimiento, BigDecimal> sinIniciar = totalesSinIniciar(soloGastos);
        System.out.println("[3] " + sinIniciar.size());
        System.out.println("[4] " + sinIniciar.get(TipoMovimiento.INGRESO));

        Map<TipoMovimiento, BigDecimal> iniciados = CalculoBolsillos.totalesPorTipo(soloGastos);
        System.out.println("[5] " + iniciados.get(TipoMovimiento.INGRESO));

        Map<String, BigDecimal> saldos = CalculoBolsillos.saldoPorBolsillo(movimientos);
        System.out.println("[6] " + saldos.get("Ahorro"));
        System.out.println("[7] " + saldos.size());

        System.out.println("[8] " + iniciados.get(TipoMovimiento.INGRESO).equals(new BigDecimal("0.00")));
    }
}
