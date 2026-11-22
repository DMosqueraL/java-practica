package dev.doris.sesion20_maven_jar;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ContarLineas {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Uso: ContarLineas <ruta del archivo>");
            return;
        }
        Path ruta = Path.of(args[0]);
        System.out.println("Ruta completa: " + ruta.toAbsolutePath());

        try {
            List<String> lineas = Files.readAllLines(ruta);
            System.out.println("Total: " + lineas.size());
        } catch (IOException error) {
            System.out.println("No se pudo leer el archivo: " + error.getMessage());
        }
    }
}
