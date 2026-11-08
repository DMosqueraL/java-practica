package dev.doris.sesion15_cargar_csv;

import dev.doris.sesion14_parseo_csv.Transaccion;

import java.util.List;

public record ResultadoCarga(List<Transaccion> validas, List<String> reporte) {
}
