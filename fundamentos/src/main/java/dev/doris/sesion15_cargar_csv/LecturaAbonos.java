package dev.doris.sesion15_cargar_csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;

import static dev.doris.sesion15_cargar_csv.EjemploCargaPagos.convertirPago;

public class LecturaAbonos {

    static BigDecimal totalValidos(Path ruta) throws IOException {
        try (BufferedReader lector = Files.newBufferedReader(ruta)) {
            lector.readLine();
            BigDecimal totalValidos = BigDecimal.ZERO;
            String linea = lector.readLine();
            while (linea != null) {
                try {
                    totalValidos = totalValidos.add(convertirPago(linea).monto());
                } catch (IllegalArgumentException error) {
                    // línea inválida: no se suma.
                }
                linea = lector.readLine();
            }
            return totalValidos;
        }
    }

    static Conteo contar(Path ruta) throws IOException {
        try (BufferedReader lector = Files.newBufferedReader(ruta)) {
            lector.readLine();
            int cantValidos = 0;
            int cantInvalidos = 0;
            String linea = lector.readLine();
            while (linea != null) {
                try {
                    convertirPago(linea);
                    cantValidos += 1;
                } catch (IllegalArgumentException error) {
                    cantInvalidos += 1;
                }
                linea = lector.readLine();
            }
            return new Conteo(cantValidos, cantInvalidos);
        }
    }

    public static void main(String[] args) throws IOException {
        Path ruta = Path.of("src/main/resources/sesion15/abonos.csv");
        // Salida: 180.12
        System.out.println("[1] " + totalValidos(ruta));
        // Salida: Conteo[vslidas=3, invalidas=3]
        System.out.println("[2] " + contar(ruta));
        // Salida: NoSuchFileException
        System.out.println("[3] " + totalValidos(Path.of("src/main/resources/sesion15/no-existe.csv")));
    }
}
