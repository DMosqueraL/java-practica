package dev.doris.sesion15_cargar_csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static dev.doris.sesion15_cargar_csv.EjemploCargaPagos.convertirPago;

public class CierreDeTema {

    static String primera(Path ruta) throws IOException {
        try (BufferedReader lector = Files.newBufferedReader(ruta)) {
            return lector.readLine();
        }
    }

    public static void main(String[] args) throws IOException {
        Path ruta = Path.of("src/main/resources/sesion15/pagos.csv");

        try (BufferedReader lector = Files.newBufferedReader(ruta)) {
            lector.readLine();
            lector.readLine();
            System.out.println("[1] " + lector.readLine());
        }

        BigDecimal total = BigDecimal.ZERO;
        int numero = 1;
        try (BufferedReader lector = Files.newBufferedReader(ruta)) {
            lector.readLine();
            String linea = lector.readLine();
            while (linea != null) {
                numero++;
                try {
                    Pago pago = convertirPago(linea);
                    total.add(pago.monto());
                } catch (IllegalArgumentException error) {
                    System.out.println("[2] " + numero);
                }
                linea = lector.readLine();
            }
        }
        System.out.println("[3] " + total);
        System.out.println("[4] " + numero);

        List<String> cuentas = new ArrayList<>();
        try (BufferedReader lector = Files.newBufferedReader(ruta)) {
            lector.readLine();
            String linea = lector.readLine();
            try {
                while (linea != null) {
                    cuentas.add(convertirPago(linea).cuenta());
                    linea = lector.readLine();
                }
            } catch (IllegalArgumentException error) {
                System.out.println("[5] " + error.getMessage());
            }
        }
        System.out.println("[6] " + cuentas);

        Path falta = Path.of("src/main/resources/sesion15/falta.csv");
        try {
            System.out.println("[7] " + primera(falta));
        } catch (IOException error) {
            System.out.println("[7] atrapada en main");
        }
        System.out.println("[8] " + primera(falta));
    }
}
