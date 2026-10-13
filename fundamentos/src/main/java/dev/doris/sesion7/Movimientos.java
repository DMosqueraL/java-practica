package dev.doris.sesion7;

import dev.doris.sesion6.Cuenta;
import dev.doris.sesion6.Movimiento;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Movimientos {

    public static Map<Cuenta, List<Movimiento>> agruparPorCuenta(List<Movimiento> movimientos) {

        Map<Cuenta, List<Movimiento>> grupoCuentas = new HashMap<>();

        for (Movimiento mov : movimientos) {
            if (!grupoCuentas.containsKey(mov.cuenta())) {
                grupoCuentas.put(mov.cuenta(), new ArrayList<>());
            }
            List<Movimiento> lista = grupoCuentas.get(mov.cuenta());
            lista.add(mov);
        }
        return grupoCuentas;
    }
}