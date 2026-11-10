package dev.doris.app_transacciones;


import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static dev.doris.app_transacciones.LineaCsv.convertir;


public class CargaTransacciones {
    static ResultadoCarga cargar(Path ruta) throws IOException {
        List<Transaccion> validas = new ArrayList<>();
        List<String> reporte = new ArrayList<>();
        try (BufferedReader lector = Files.newBufferedReader(ruta)) {
            lector.readLine();
            String linea = lector.readLine();
            int numero = 1;
            while (linea != null) {
                numero++;
                try {
                    validas.add(convertir(linea));
                } catch (IllegalArgumentException error) {
                    reporte.add("Línea " + numero + ": " + error.getMessage());
                }
                linea = lector.readLine();
            }
        }
        return new ResultadoCarga(validas, reporte);
    }

    public static void main(String[] args) throws IOException {
        Path ruta = Path.of("src/main/resources/transacciones.csv");
        ResultadoCarga resultado = cargar(ruta);
        // Salida: 10
        System.out.println(resultado.validas().size());
        // Salida:
        // [Línea 7: Monto inválido: abc,
        // Línea 9: fecha inválida: 31/6/2026,
        // Línea 10: tipo inválido: RETIRO,
        // Línea 12: columnas: 3,
        // Línea 14: columnas: 6]
        System.out.println(resultado.reporte());
    }
}
