package dev.doris.sesion15_cargar_csv;

import java.util.List;

public record ResultadoPagos(List<Pago> validos, List<String> errores) {
}
