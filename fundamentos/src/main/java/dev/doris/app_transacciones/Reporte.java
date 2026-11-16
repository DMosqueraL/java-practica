package dev.doris.app_transacciones;

import java.math.BigDecimal;
import java.util.Map;

public class Reporte {
    public static String armar(ResultadoCarga resultado,
                               Map<TipoTransaccion, BigDecimal> totales,
                               Map<String, BigDecimal> saldos) {
        String linea = "";
        for (int i = 0; i < resultado.reporte().size(); i++) {
            linea = linea + "\n" + resultado.reporte().get(i);
        }

        String total = "";
        for (TipoTransaccion tipo : TipoTransaccion.values()) {
            total = total + "\n" + String.format("%-8s%12s", tipo, totales.get(tipo).setScale(2));
        }

        String saldo = "";
        for (String cuenta : saldos.keySet()) {
            saldo = saldo + "\n" + String.format("%-8s%12s", cuenta, saldos.get(cuenta).setScale(2));
        }
        return "Válidas: " + resultado.validas().size() + "\n" +
                "Inválidas: " + resultado.reporte().size() + linea + "\n" +
                "Totales por tipo:" + total + "\n" + "Saldos por cuenta:" + saldo;
    }
}
