package dev.doris.sesion16_app_consola;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LecturaDepositos {
    public static List<Deposito> leer(Path ruta) throws IOException {
        List<Deposito> depositos = new ArrayList<>();
        try (BufferedReader lector = Files.newBufferedReader(ruta)) {
            lector.readLine();
            String linea = lector.readLine();
            while (linea != null) {
                String[] partes = linea.split(",", -1);
                depositos.add(new Deposito(partes[0].trim(), new BigDecimal(partes[1].trim())));
                linea = lector.readLine();
            }
        }
        return depositos;
    }
}
