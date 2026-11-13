package dev.doris.sesion17_calculos;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class Parte2SaldoPorBolsillo {
    public static void main(String[] args) {
        List<Transaccion> movimientos = List.of(
                new Transaccion("Ahorro", TipoMovimiento.INGRESO, new BigDecimal("200.00")),
                new Transaccion("Viajes", TipoMovimiento.INGRESO, new BigDecimal("80.00")),
                new Transaccion("Ahorro", TipoMovimiento.GASTO, new BigDecimal("50.25")),
                new Transaccion("Mercado", TipoMovimiento.GASTO, new BigDecimal("30.25")),
                new Transaccion("Viajes", TipoMovimiento.GASTO, new BigDecimal("100.50")),
                new Transaccion("Ahorro", TipoMovimiento.INGRESO, new BigDecimal("10.00")));

        Map<String, BigDecimal> saldos = CalculoBolsillos.saldoPorBolsillo(movimientos);
        System.out.println("[1] Ahorro: " + saldos.get("Ahorro"));
        System.out.println("[2] Viajes: " + saldos.get("Viajes"));
        System.out.println("[3] Mercado: " + saldos.get("Mercado"));
        System.out.println("[4] bolsillos: " + saldos.size());
        System.out.println("[5] Regalos: " + saldos.get("Regalos"));
    }
}
