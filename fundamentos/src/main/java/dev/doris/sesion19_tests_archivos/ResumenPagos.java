package dev.doris.sesion19_tests_archivos;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ResumenPagos {
    public static BigDecimal total(Path archivo) throws IOException {
        List<String> lineas = Files.readAllLines(archivo);
        BigDecimal total = BigDecimal.ZERO;
        for (int i = 1; i < lineas.size(); i++) {
            String[] partes = lineas.get(i).split(",");
            total = total.add(new BigDecimal(partes[1].trim()));
        }
        return total;
    }
}
