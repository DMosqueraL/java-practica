package dev.doris.sesion09_excepciones;

import java.math.BigDecimal;
import java.util.List;

public class Abonos {
    public static BigDecimal sumarMontos(List<Abono> abonos) {

        if (abonos == null) {
            throw new IllegalArgumentException("La lista no puede ser nula.");
        }
        BigDecimal total = BigDecimal.ZERO;
        for (Abono abono : abonos) {
            total = total.add(abono.monto());
        }
        return total;
    }

    public static Abono crear(String cuenta, BigDecimal monto) {
        if (cuenta == null) {
            throw new IllegalArgumentException("La cuenta no puede ser nula.");
        }
        if (cuenta.length() != 4) {
            throw new IllegalArgumentException("La cuenta debe ser exactamente 4 caracteres. La cuenta ingresada tiene: " + cuenta.length());
        }
        if (monto == null) {
            throw new IllegalArgumentException("El monto no puede ser nulo.");
        }
        if (monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto ingresado: $" + monto + ", no puede ser cero o negativo.");
        }
        return new Abono(cuenta, monto);
    }
}
