package dev.doris.sesion15_cargar_csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class EjemploCargaPagos {

    static Pago convertirPago(String linea) {
        String[] partes = linea.split(",", -1);
        if (partes.length != 2) {
            throw new IllegalArgumentException("columnas: " + partes.length);
        }
        String cuenta = partes[0].trim();
        BigDecimal monto;
        try {
            monto = new BigDecimal(partes[1].trim()).setScale(2, RoundingMode.HALF_EVEN);
        } catch (NumberFormatException error) {
            throw new IllegalArgumentException("monto inválido: " + partes[1].trim());
        }
        return new Pago(cuenta, monto);
    }

    // Versión 1: el try/catch envuelve el bucle completo
    static List<Pago> cargarCortando(Path ruta) throws IOException {
        List<Pago> validos = new ArrayList<>();
        try (BufferedReader lector = Files.newBufferedReader(ruta)) {
            lector.readLine();
            String linea = lector.readLine();
            try {
                while (linea != null) {
                    validos.add(convertirPago(linea));
                    linea = lector.readLine();
                }
            } catch (IllegalArgumentException error) {
                System.out.println("se cortó: " + error.getMessage());
            }
        }
        return validos;
    }

    // Versión 2: el try/catch está dentro del bucle
    static ResultadoPagos cargar(Path ruta) throws IOException {
        List<Pago> validos = new ArrayList<>();
        List<String> errores = new ArrayList<>();
        try (BufferedReader lector = Files.newBufferedReader(ruta)) {
            lector.readLine();
            int numero = 1;
            String linea = lector.readLine();
            while (linea != null) {
                numero++;
                try {
                    validos.add(convertirPago(linea));
                } catch (IllegalArgumentException error) {
                    errores.add("línea " + numero + ": " + error.getMessage());
                }
                linea = lector.readLine();
            }
        }
        return new ResultadoPagos(validos, errores);
    }

    public static void main(String[] args) throws IOException {
        Path ruta = Path.of("src/main/resources/sesion15/pagos.csv");

        List<Pago> cortados = cargarCortando(ruta);
        System.out.println("[1] " + cortados.size());

        ResultadoPagos resultado = cargar(ruta);
        System.out.println("[2] " + resultado.validos().size());
        System.out.println("[3] " + resultado.errores().size());
        for (String error : resultado.errores()) {
            System.out.println("[4] " + error);
        }
        System.out.println("[5] " + resultado.validos().get(1));
    }
}
