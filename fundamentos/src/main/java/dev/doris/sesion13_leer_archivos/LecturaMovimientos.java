package dev.doris.sesion13_leer_archivos;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LecturaMovimientos {

    static int contarLineas(Path ruta) throws IOException {
        try (BufferedReader lector = Files.newBufferedReader(ruta)) {
            int contador = 0;
            int numero = 1;
            String linea = lector.readLine();
            while (linea != null) {
                contador += 1;
                numero++;
                linea = lector.readLine();
            }
            return contador;
        }
    }

    static List<String> numerar(Path ruta) throws IOException {
        List<String> lineas = Files.readAllLines(ruta);
        List<String> resultado = new ArrayList<>();
        for (int i = 0; i < lineas.size(); i++) {
            resultado.add(i + 1 + ": " + lineas.get(i));
        }
        return resultado;
    }

    public static void main(String[] args) throws IOException {
        Path ruta = Path.of("src/main/resources/sesion13/movimientos.txt");
        Path vacio = Path.of("src/main/resources/sesion13/vacio.txt");
        Path noExiste = Path.of("src/main/resources/sesion13/no-existe.txt");

        // Salida: [1] 3 lineas
        System.out.println("[1] " + contarLineas(ruta) + " lineas");
        // Salida: [2] 0 lineas
        System.out.println("[2] " + contarLineas(vacio) + " lineas");
        // Salida: [3] [1: 0001;DEBITO;150.00, 2: 0002;CREDITO;80.50, 3: 0003;DEBITO;20.00]
        System.out.println("[3] " + numerar(ruta));
        // Salida: [4] []
        System.out.println("[4] " + numerar(vacio));
        // Salida: [5] no existe: src/main/resources/sesion13/no-existe.txt
        try {
            System.out.println("[5] " + numerar(noExiste));
        } catch (NoSuchFileException error) {
            System.out.println("[5] no existe: " + error.getMessage());
        }
        // Salida: [6] no existe: src/main/resources/sesion13/no-existe.txt
        try {
            System.out.println("[6] " + contarLineas(noExiste));
        } catch (NoSuchFileException error) {
            System.out.println("[6] no existe: " + error.getMessage());
        }

    }
}
