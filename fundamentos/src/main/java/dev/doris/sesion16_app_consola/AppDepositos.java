package dev.doris.sesion16_app_consola;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.List;

public class AppDepositos {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Uso: AppDepositos <ruta del CSV>");
            return;
        }
        Path ruta = Path.of(args[0]);

        try {
            List<Deposito> depositos = LecturaDepositos.leer(ruta);              // 1. lectura
            BigDecimal total = CalculoDepositos.total(depositos);                // 2. cálculo
            String reporte = ReporteDepositos.armar(depositos.size(), total);    // 3. reporte
            System.out.println(reporte);
        } catch (IOException error) {
            System.out.println("No se pudo leer el archivo: " + error.getMessage());
        }
    }
}
