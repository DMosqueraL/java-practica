package dev.doris.sesion20_maven_jar;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ExtremosCsv {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Uso: ExtremosCsv <ruta del CSV>");
            return;
        }
        Path ruta = Path.of(args[0]);

        try {
            List<String> lineas = Files.readAllLines(ruta);
            // Salida con java -jar fundamentos\target\fundamentos-1.0.0-SNAPSHOT.jar fundamentos\src\main\resources\transacciones.csv
            // Encabezado: fecha,cuenta,tipo,monto
            // Final: 14/6/2026,0001,DEBITO,5.5
            // Salida con java -jar fundamentos\target\fundamentos-1.0.0-SNAPSHOT.jar resources\transacciones.csv
            // No se pudo leer el archivo: resources\transacciones.csv
            System.out.println("Encabezado: " + lineas.get(0));
            System.out.println("Final: " + lineas.get(lineas.size() - 1));

        } catch (IOException e) {
            System.out.println("No se pudo leer el archivo: " + e.getMessage());
        }
    }
}
