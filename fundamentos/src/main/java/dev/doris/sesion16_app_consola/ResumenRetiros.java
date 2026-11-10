package dev.doris.sesion16_app_consola;

import java.math.BigDecimal;
import java.util.List;

public record ResumenRetiros(BigDecimal total, List<String> reporte) {
}
