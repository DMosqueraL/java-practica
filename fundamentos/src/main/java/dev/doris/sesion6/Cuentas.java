package dev.doris.sesion6;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Cuentas {
    public static Set<Cuenta> cuentasDistintas(List<Movimiento> movimientos){
        Set<Cuenta> resultado = new HashSet<>();
        for (Movimiento mov : movimientos){
            resultado.add(mov.cuenta());
        }
        return resultado;
    }

    public static boolean hayCuentasRepetidas(List<Cuenta> cuentas){
        Set<Cuenta> vistas = new HashSet<>();
        for (Cuenta cuenta : cuentas){
            if (!vistas.add(cuenta)) return true;
        }
        return false;
    }
}
