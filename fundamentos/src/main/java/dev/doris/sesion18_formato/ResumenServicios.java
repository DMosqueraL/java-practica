package dev.doris.sesion18_formato;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ResumenServicios {
    static String armar(List<PagoServicio> pagos) {
        String linea = String.format("%-10s%6s%12s", "Servicio", "Pagos", "Total");

        for (int i = 0; i < pagos.size(); i++) {
            linea = linea + "\n" + String.format("%-10s%6s%12s", pagos.get(i).servicio(), pagos.get(i).cantidad(), pagos.get(i).total().setScale(2));
        }
        linea = linea + "\n" + String.format("Servicios: %d", pagos.size());
        return linea;
    }

    public static void main(String[] args) {
        List<PagoServicio> pagos = new ArrayList<>();
        pagos.add(new PagoServicio("Internet", 1, new BigDecimal("120.00")));
        pagos.add(new PagoServicio("Agua", 2, new BigDecimal("45.5")));
        pagos.add(new PagoServicio("Gas", 0, BigDecimal.ZERO));

        System.out.println(armar(pagos));


    }
}
