package dev.doris.app_transacciones;

import java.util.List;

public record ResultadoCarga(List<Transaccion> validas, List<String> reporte) {
}
