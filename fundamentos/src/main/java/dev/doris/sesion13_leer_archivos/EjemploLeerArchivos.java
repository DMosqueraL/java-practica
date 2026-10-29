package dev.doris.sesion13_leer_archivos;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;

public class EjemploLeerArchivos {

    public static void main(String[] args) throws IOException {
        // [1] Path: guarda la ruta del archivo; todavía no lo abre
        Path ruta = Path.of("src/main/resources/sesion13/movimientos.txt");
        System.out.println("[1] " + ruta);

        // [2] Files.readAllLines: abre, lee todo, cierra y devuelve una List<String>
        List<String> lineas = Files.readAllLines(ruta);
        System.out.println("[2] " + lineas.size() + " lineas: " + lineas);

        // [3] Archivo vacío: lista vacía, no null
        List<String> vacias = Files.readAllLines(Path.of("src/main/resources/sesion13/vacio.txt"));
        System.out.println("[3] " + vacias.size() + " " + vacias);

        // [4] BufferedReader dentro de un try con recursos: una línea a la vez
        try (BufferedReader lector = Files.newBufferedReader(ruta)) {
            int numero = 1;
            String linea = lector.readLine();
            while (linea != null) {
                System.out.println("[4] linea " + numero + ": " + linea);
                numero++;
                linea = lector.readLine();
            }
        }

        // [5] Archivo que no existe
        try {
            Files.readAllLines(Path.of("src/main/resources/sesion13/no-existe.txt"));
        } catch (NoSuchFileException error) {
            System.out.println("[5] no existe: " + error.getMessage());
        }
    }
}
