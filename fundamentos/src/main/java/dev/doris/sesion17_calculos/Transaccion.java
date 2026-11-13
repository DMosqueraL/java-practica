package dev.doris.sesion17_calculos;

import java.math.BigDecimal;

public record Transaccion(String bolsillo, TipoMovimiento tipo, BigDecimal monto) {
}
