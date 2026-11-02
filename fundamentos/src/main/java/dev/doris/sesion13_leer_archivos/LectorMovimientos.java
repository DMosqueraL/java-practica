package dev.doris.sesion13_leer_archivos;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LectorMovimientos {

    static List<String> leerLineas(Path ruta) throws IOException {
        List<String> lista = new ArrayList<>();
        try (BufferedReader lector = Files.newBufferedReader(ruta)) {
            String linea = lector.readLine();
            while (linea != null) {
                lista.add(linea);
                linea = lector.readLine();
            }
        }
        return lista;
    }
}
