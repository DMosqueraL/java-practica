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
            String reporte = Reporte.armar(resultado);
            System.out.println(reporte);
            //Predicción: DEBITO = 337.70 y CREDITO = 2920.74
            for (TipoTransaccion tipo : TipoTransaccion.values()) {
                BigDecimal total = totales.get(tipo);
                System.out.println(tipo + " = " + total);
            }
            //Predicción:
            //Cuenta 0001: 1343.99
            //Cuenta 0002: 764.20
            //Cuenta 0003: 474.85
            for (String cuenta : saldos.keySet()) {
                BigDecimal saldo = saldos.get(cuenta);
                System.out.println("Cuenta " + cuenta + ": " + saldo);
            }
        } catch (IOException error) {
            System.out.println("No se pudo leer el archivo: " + error.getMessage());
        }
    }
}
