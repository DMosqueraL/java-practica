package dev.doris.sesion10_excepciones_propias;

import java.math.BigDecimal;

public record Movimiento(String cuenta, TipoMovimiento tipo, BigDecimal monto) {
}
