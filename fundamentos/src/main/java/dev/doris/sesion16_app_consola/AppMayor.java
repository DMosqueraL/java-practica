package dev.doris.sesion16_app_consola;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.List;

public class AppMayor {
    // Salida:
    // Con "src/main/resources/sesion16/depositos.csv" ---> Mayor depósito: 100.00
    // Con (vacío) ---> Uso: AppMayor <ruta del CSV>
    // Con "src/main/resources/sesion16/no-existe.csv" ---> No se pudo leer el archivo: src\main\resources\sesion16\no-existe.csv
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Uso: AppMayor <ruta del CSV>");
            return;
        }
        Path ruta = Path.of(args[0]);

        try {
            List<Deposito> depositos = LecturaDepositos.leer(ruta);
            BigDecimal mayor = CalculoDepositos.mayorMonto(depositos);
            String reporte = ReporteDepositos.armarMayor(mayor);
            System.out.println(reporte);
        } catch (IOException error) {
            System.out.println("No se pudo leer el archivo: " + error.getMessage());
        }
    }
}
