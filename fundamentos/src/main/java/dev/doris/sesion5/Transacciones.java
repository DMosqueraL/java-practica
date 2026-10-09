package dev.doris.sesion5;

import java.util.ArrayList;
import java.util.List;

public class Transacciones {
    static List<Transaccion> filtrarPorTipo(List<Transaccion> lista, TipoTransaccion tipo){
        List<Transaccion> listaPorTipo = new ArrayList<>();
        for (Transaccion trx : lista){
            if (trx.tipo() == tipo){
                listaPorTipo.add(trx);
            }
        }
        return listaPorTipo;
    }

    static double sumarMontos(List<Transaccion> lista){
        double total = 0;
        for (Transaccion trx : lista){
            total += trx.monto();
        }
        return  total;
    }
}
