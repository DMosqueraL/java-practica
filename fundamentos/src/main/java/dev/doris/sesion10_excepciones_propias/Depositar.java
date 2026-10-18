package dev.doris.sesion10_excepciones_propias;

import java.math.BigDecimal;

class CuentaCongeladaException extends RuntimeException {
    public CuentaCongeladaException(String cuenta) {
        super("La cuenta " + cuenta + " está congelada.");
    }
}

class LimiteExcedidoException extends RuntimeException {
    private final BigDecimal exceso;

    public LimiteExcedidoException(BigDecimal exceso) {
        super("Límite excedido. Exceso $" + exceso);
        this.exceso = exceso;
    }

    public BigDecimal exceso() {
        return exceso;
    }
}

public class Depositar {
    static BigDecimal depositar(String cuenta, BigDecimal saldo, BigDecimal monto, BigDecimal limite) {
        // Regla 1 - Excepción Estándar
        if (cuenta == null) {
            throw new IllegalArgumentException("La cuenta no puede ser nula.");
        }
        // Regla 2 - Excepción Estándar
        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero. Monto ingresado: " + monto);
        }
        // Regla 3 - Excepción Propia
        if (cuenta.equals("0000")) {
            throw new CuentaCongeladaException(cuenta);
        }
        // Regla 4 - Excepción Propia
        BigDecimal nuevoSaldo = saldo.add(monto);
        if (nuevoSaldo.compareTo(limite) > 0) {
            throw new LimiteExcedidoException(nuevoSaldo.subtract(limite));
        }
        // Regla 5 - Camino Feliz
        return nuevoSaldo;
    }
}
