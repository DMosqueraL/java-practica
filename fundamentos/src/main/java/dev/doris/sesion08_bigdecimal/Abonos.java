package dev.doris.sesion08_bigdecimal;

import java.math.BigDecimal;
import java.util.List;

public class Abonos {
    public static BigDecimal sumarMontos(List<Abono> abonos) {
        BigDecimal total = BigDecimal.ZERO;
        for (Abono abono : abonos) {
            total = total.add(abono.monto());
        }
        return total;
    }
}
