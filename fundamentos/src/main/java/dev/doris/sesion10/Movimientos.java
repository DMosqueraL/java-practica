package dev.doris.sesion10;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class CuentaInvalidaException extends RuntimeException {
    public CuentaInvalidaException(String cuenta) {
        super("La cuenta " + cuenta + " es inválida. No tiene exactamente 4 caracteres.");
    }
}

class MontoInvalidoException extends RuntimeException {
    private final BigDecimal monto;

    public MontoInvalidoException(BigDecimal monto) {
        super("El monto " + monto + " es inválido. El monto debe ser mayor que cero.");
        this.monto = monto;
    }

    public BigDecimal monto() {
        return monto;
    }
}

public class Movimientos {
    static Movimiento crear(String cuenta, TipoMovimiento tipo, BigDecimal monto) {
        if (cuenta == null || tipo == null || monto == null) {
            throw new IllegalArgumentException("La cuenta, el tipo de movimiento y/o el monto no pueden ser nulos.");
        }
        if (cuenta.length() != 4) {
            throw new CuentaInvalidaException(cuenta);
        }
        if (monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new MontoInvalidoException(monto);
        }
        return new Movimiento(cuenta, tipo, monto);
    }

    static Map<TipoMovimiento, BigDecimal> totalesPorTipo(List<Movimiento> movimientos) {
        if (movimientos == null) {
            throw new IllegalArgumentException("La lista no puede ser nula.");
        }

        Map<TipoMovimiento, BigDecimal> totalPorTipo = new HashMap<>();

        for (Movimiento mov : movimientos) {
            BigDecimal cantPorTipo = totalPorTipo.getOrDefault(mov.tipo(), BigDecimal.ZERO);
            BigDecimal totalTipoMov = cantPorTipo.add(mov.monto());
            totalPorTipo.put(mov.tipo(), totalTipoMov);
        }
        return totalPorTipo;
    }

    static Map<String, List<Movimiento>> agruparPorCuenta(List<Movimiento> movimientos) {
        if (movimientos == null) {
            throw new IllegalArgumentException("La lista no puede ser nula.");

        }

        Map<String, List<Movimiento>> grupoPorCuenta = new HashMap<>();

        for (Movimiento mov : movimientos) {
            if (!grupoPorCuenta.containsKey(mov.cuenta())) {
                grupoPorCuenta.put(mov.cuenta(), new ArrayList<>());
            }
            List<Movimiento> lista = grupoPorCuenta.get(mov.cuenta());
            lista.add(mov);
        }
        return grupoPorCuenta;
    }
}
