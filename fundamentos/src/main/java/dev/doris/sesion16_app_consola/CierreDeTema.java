package dev.doris.sesion16_app_consola;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.List;

public class CierreDeTema {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("[1] uso");
            return;
        }
        try {
            List<Deposito> depositos = LecturaDepositos.leer(Path.of(args[0]));
            BigDecimal total = CalculoDepositos.total(depositos);
            System.out.println("[2] " + ReporteDepositos.armar(depositos.size(), total));
        } catch (IOException error) {
            System.out.println("[3] " + error.getMessage());
        }
        System.out.println("[4] fin");
    }
}
