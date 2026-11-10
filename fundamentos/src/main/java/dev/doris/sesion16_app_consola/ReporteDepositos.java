package dev.doris.sesion16_app_consola;

import java.math.BigDecimal;

public class ReporteDepositos {
    public static String armar(int cantidad, BigDecimal total) {
        return "Depósitos: " + cantidad + "\n" + "Total: " + total;
    }

    public static String armarMayor(BigDecimal mayor) {
        return "Mayor depósito: " + mayor;
    }
}
