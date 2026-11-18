package dev.doris.sesion19_tests_archivos;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ReportePagos {
    public static String armar(Path archivo) throws IOException {
        List<String> lineas = Files.readAllLines(archivo);
        BigDecimal total = BigDecimal.ZERO;
        String invalidas = "";
        for (int i = 1; i < lineas.size(); i++) {
            try {
                total = total.add(monto(lineas.get(i)));
            } catch (IllegalArgumentException e) {
                invalidas = invalidas + "\n" + "Línea " + (i + 1) + ": " + e.getMessage();
            }
        }
        return String.format("%-8s%10s", "Total", total.setScale(2)) + invalidas;
    }

    static BigDecimal monto(String linea) {
        String[] partes = linea.split(",", -1);
        if (partes.length != 2) {
            throw new IllegalArgumentException("columnas inválidas");
        }
        return new BigDecimal(partes[1].trim());
    }
}
