package dev.doris.sesion16_app_consola;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Retiro(LocalDate fecha, String cajero, BigDecimal monto) {
}
