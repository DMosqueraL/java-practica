package dev.doris.app_transacciones;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalculoTransacciones {
    public static Map<TipoTransaccion, BigDecimal> totalesPorTipo(List<Transaccion> transacciones) {
        Map<TipoTransaccion, BigDecimal> totalPorTipo = new HashMap<>();

        for (TipoTransaccion tipo : TipoTransaccion.values()) {
            totalPorTipo.put(tipo, BigDecimal.ZERO);
        }

        for (Transaccion transaccion : transacciones) {
            BigDecimal actual = totalPorTipo.get(transaccion.tipo());
            totalPorTipo.put(transaccion.tipo(), actual.add(transaccion.monto()));
        }
        return totalPorTipo;
    }

    public static Map<String, BigDecimal> saldoPorCuenta(List<Transaccion> transacciones) {
        Map<String, BigDecimal> saldoPorCuenta = new HashMap<>();

        for (Transaccion transaccion : transacciones) {
            BigDecimal saldo = saldoPorCuenta.getOrDefault(transaccion.cuenta(), BigDecimal.ZERO);
            if (transaccion.tipo() == TipoTransaccion.CREDITO) {
                saldo = saldo.add(transaccion.monto());
            } else {
                saldo = saldo.subtract(transaccion.monto());
            }
            saldoPorCuenta.put(transaccion.cuenta(), saldo);
        }
        return saldoPorCuenta;
    }

    public static void main(String[] args) {
        List<Transaccion> transacciones = List.of(
                new Transaccion(LocalDate.of(2026, 6, 1), "0001", TipoTransaccion.CREDITO, new BigDecimal("100.00")),
                new Transaccion(LocalDate.of(2026, 6, 2), "0002", TipoTransaccion.DEBITO, new BigDecimal("20.50")),
                new Transaccion(LocalDate.of(2026, 6, 3), "0001", TipoTransaccion.DEBITO, new BigDecimal("10.25")),
                new Transaccion(LocalDate.of(2026, 6, 4), "0003", TipoTransaccion.CREDITO, new BigDecimal("50.00")));

        Map<String, BigDecimal> saldoPorCuenta = CalculoTransacciones.saldoPorCuenta(transacciones);
        System.out.println(saldoPorCuenta);
    }
}
