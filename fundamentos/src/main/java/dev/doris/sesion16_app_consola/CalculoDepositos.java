package dev.doris.sesion16_app_consola;

import java.math.BigDecimal;
import java.util.List;

public class CalculoDepositos {
    public static BigDecimal total(List<Deposito> depositos) {
        BigDecimal total = BigDecimal.ZERO;
        for (Deposito deposito : depositos) {
            total = total.add(deposito.monto());
        }
        return total;
    }

    public static BigDecimal mayorMonto(List<Deposito> depositos) {
        BigDecimal mayor = BigDecimal.ZERO;
        for (Deposito deposito : depositos) {
            if (deposito.monto().compareTo(mayor) > 0) {
                mayor = deposito.monto();
            }
        }
        return mayor;
    }
}
