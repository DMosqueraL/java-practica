package dev.doris.sesion17_calculos;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalculoBolsillos {

    public static Map<TipoMovimiento, BigDecimal> totalesPorTipo(List<Transaccion> movimientos) {
        Map<TipoMovimiento, BigDecimal> totales = new HashMap<>();

        // 1. Todas las claves en cero: un tipo sin movimientos queda con 0, no falta
        for (TipoMovimiento tipo : TipoMovimiento.values()) {
            totales.put(tipo, BigDecimal.ZERO);
        }

        // 2. Acumular: la clave ya existe, así que get nunca devuelve null
        for (Transaccion movimiento : movimientos) {
            BigDecimal actual = totales.get(movimiento.tipo());
            totales.put(movimiento.tipo(), actual.add(movimiento.monto()));
        }
        return totales;
    }

    public static Map<String, BigDecimal> saldoPorBolsillo(List<Transaccion> movimientos) {
        Map<String, BigDecimal> saldos = new HashMap<>();

        for (Transaccion movimiento : movimientos) {
            BigDecimal saldo = saldos.getOrDefault(movimiento.bolsillo(), BigDecimal.ZERO);
            if (movimiento.tipo() == TipoMovimiento.INGRESO) {
                saldo = saldo.add(movimiento.monto());          // un ingreso suma
            } else {
                saldo = saldo.subtract(movimiento.monto());     // un gasto resta
            }
            saldos.put(movimiento.bolsillo(), saldo);
        }
        return saldos;
    }
}
