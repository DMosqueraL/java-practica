package dev.doris.app_transacciones;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.Map;

import static dev.doris.app_transacciones.CargaTransacciones.cargar;

public class App {
    // Salida:
    // Con vacío ---> Uso: App <ruta del CSV>
    // Con "src/main/resources/transacciones.csv" ---> Válidas: 10
    //                                                 Inválidas: 5
    //                                                 Línea 7: Monto inválido: abc
    //                                                 Línea 9: fecha Inválida: 31/6/2026
    //                                                 Línea 10: tipo inválido: RETIRO
    //                                                 Línea 12: Columnas: 3
    //                                                 Línea 14: Columnas: 6
    //                                                 Totales por tipo:
    //                                                 DEBITO        337.70
    //                                                 CREDITO      2920.74
    //                                                 Saldos por cuenta:
    //                                                 0001         1343.99
    //                                                 0002          764.20
    //                                                 0003          474.85
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Uso: App <ruta del CSV>");
            return;
        }
        Path ruta = Path.of(args[0]);

        try {
            ResultadoCarga resultado = cargar(ruta);
            Map<TipoTransaccion, BigDecimal> totales = CalculoTransacciones.totalesPorTipo(resultado.validas());
            Map<String, BigDecimal> saldos = CalculoTransacciones.saldoPorCuenta(resultado.validas());
            String reporte = Reporte.armar(resultado, totales, saldos);
            System.out.println(reporte);
        } catch (IOException error) {
            System.out.println("No se pudo leer el archivo: " + error.getMessage());
        }
    }
}
