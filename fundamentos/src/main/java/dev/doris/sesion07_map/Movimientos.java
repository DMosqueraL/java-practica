package dev.doris.sesion07_map;

import dev.doris.sesion06_set_hashcode.Cuenta;
import dev.doris.sesion06_set_hashcode.Movimiento;

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