package dev.doris.sesion11_java_time;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Transaccion(String cuenta, BigDecimal monto, LocalDate fecha) {
}
