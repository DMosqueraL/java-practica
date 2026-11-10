package dev.doris.app_transacciones;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Transaccion(LocalDate fecha, String cuenta, TipoTransaccion tipo, BigDecimal monto) {
}
