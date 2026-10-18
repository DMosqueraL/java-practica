package dev.doris.sesion10_excepciones_propias;

import java.math.BigDecimal;

class TopeDiarioException extends RuntimeException {
    private final BigDecimal sobrante;

    public TopeDiarioException(BigDecimal sobrante) {
        super("Tope diario superado por " + sobrante);
        this.sobrante = sobrante;
    }

    public BigDecimal sobrante() {
        return sobrante;
    }
}

class CuentaCerradaException extends RuntimeException {
    public CuentaCerradaException(String cuenta) {
        super("Cuenta cerrada: " + cuenta);
    }
}

public class CierreTema {

    static BigDecimal transferir(String cuenta, BigDecimal monto, BigDecimal usadoHoy) {
        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Monto invalido: " + monto);
        }
        if (cuenta.equals("0000")) {
            throw new CuentaCerradaException(cuenta);
        }
        BigDecimal total = usadoHoy.add(monto);
        BigDecimal tope = new BigDecimal("500.00");
        if (total.compareTo(tope) > 0) {
            throw new TopeDiarioException(total.subtract(tope));
        }
        return total;
    }

    public static void main(String[] args) {
        BigDecimal cero = new BigDecimal("0.00");
        BigDecimal cien = new BigDecimal("100.00");

        System.out.println("[1] " + transferir("0001", cien, new BigDecimal("50.00")));

        try {
            transferir("0001", new BigDecimal("300.00"), new BigDecimal("250.00"));
            System.out.println("[2] hecho");
        } catch (TopeDiarioException e) {
            System.out.println("[3] " + e.getMessage());
            System.out.println("[4] " + e.sobrante());
        }

        try {
            transferir("0000", cien, cero);
        } catch (TopeDiarioException e) {
            System.out.println("[5] tope");
        } catch (CuentaCerradaException e) {
            System.out.println("[6] " + e.getMessage());
        }

        try {
            transferir("0001", cero, cero);
        } catch (IllegalArgumentException e) {
            System.out.println("[7] " + e.getMessage());
        }

        try {
            transferir(null, cien, cero);
        } catch (IllegalArgumentException e) {
            System.out.println("[8] " + e.getMessage());
        }
    }
}
