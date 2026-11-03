package dev.doris.sesion14_parseo_csv;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Transaccion(LocalDate fecha, String cuenta, TipoTransaccion tipo, BigDecimal monto) {
}
