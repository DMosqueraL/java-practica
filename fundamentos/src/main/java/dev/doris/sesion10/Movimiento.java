package dev.doris.sesion10;

import java.math.BigDecimal;

public record Movimiento(String cuenta, TipoMovimiento tipo, BigDecimal monto) {
}
