package dev.doris.sesion18_formato;

import java.math.BigDecimal;

public record PagoServicio(String servicio, int cantidad, BigDecimal total) {
}
