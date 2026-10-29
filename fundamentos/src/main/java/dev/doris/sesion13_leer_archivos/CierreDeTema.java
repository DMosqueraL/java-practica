package dev.doris.sesion13_leer_archivos;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;

public class CierreDeTema {

    public static void main(String[] args) throws IOException {
        Path ruta = Path.of("src/main/resources/sesion13/movimientos.txt");
        Path vacio = Path.of("src/main/resources/sesion13/vacio.txt");

        Path falta = Path.of("src/main/resources/sesion13/falta.txt");
        System.out.println("[1] ruta creada");

        List<String> lineas = Files.readAllLines(ruta);
        System.out.println("[2] " + lineas.get(1));

        System.out.println("[3] " + (Files.readAllLines(vacio) == null));

        try {
            System.out.println("[4] " + Files.readAllLines(vacio).size());
        } catch (NoSuchFileException error) {
            System.out.println("[4] no existe");
        }

        try (BufferedReader lector = Files.newBufferedReader(vacio)) {
            System.out.println("[5] " + lector.readLine());
        }

        try (BufferedReader lector = Files.newBufferedReader(ruta)) {
            lector.readLine();
            String linea = lector.readLine();
            System.out.println("[6] " + linea);
        }

        String ultima = "";
        try (BufferedReader lector = Files.newBufferedReader(ruta)) {
            String linea = lector.readLine();
            while (linea != null) {
                ultima = linea;
                linea = lector.readLine();
            }
        }
        System.out.println("[7] " + ultima);

        try {
            Files.readAllLines(falta);
            System.out.println("[8] leido");
        } catch (IOException error) {
            System.out.println("[8] atrapada");
        }
    }
}
