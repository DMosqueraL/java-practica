package dev.doris.sesion4;

import java.util.List;

public class Main2 {
    static double sumaMontos(List<Transaccion> lista){
        double total = 0;
        for (Transaccion trx : lista){
            total += trx.monto();
        }
        return total;
    }
    public static void main(String[] args){

        List<Transaccion> lista = List.of(
                new Transaccion("1", 1475, TipoTransaccion.CREDITO),
                new Transaccion("2", 550, TipoTransaccion.DEBITO),
                new Transaccion("3", 1100, TipoTransaccion.DEBITO),
                new Transaccion("4", 5000, TipoTransaccion.CREDITO));

        //Predicción:
        //Transacción de Débito: [2, 550.0, DEBITO]
        //Transacción de Débito: [3, 1100.0, DEBITO]
        for (Transaccion trx : lista){
            if (trx.tipo() == TipoTransaccion.DEBITO){
                System.out.println("Transacción de Débito: [" +
                        trx.id() + ", " + trx.monto() + ", " + trx.tipo() + "]");
            }
        }
        //Predicción:
        //Total de los montos = $8125.0
        System.out.println("Total de los montos = $" + sumaMontos(lista));
    }
}
