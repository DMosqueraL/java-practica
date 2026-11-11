package dev.doris.app_transacciones;

public class Reporte {
    public static String armar(ResultadoCarga resultado) {
        String linea = "";
        for (int i = 0; i < resultado.reporte().size(); i++) {
            linea = linea + "\n" + resultado.reporte().get(i);
        }
        return "Válidas: " + resultado.validas().size() + "\n" +
                "Inválidas: " + resultado.reporte().size() + linea;
    }
}
