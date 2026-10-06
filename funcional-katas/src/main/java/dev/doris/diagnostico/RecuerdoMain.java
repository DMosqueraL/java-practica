package dev.doris.diagnostico;

import java.util.List;

public class RecuerdoMain {

    public static void main(String[] args){

        Transaccion trx1 = new Transaccion("1", 500, "CREDITO");
        Transaccion trx2 = new Transaccion("2", 1250.25, "DEBITO");
        Transaccion trx3 = new Transaccion("3", 259.99, "CREDITO");
        Transaccion trx4 = new Transaccion("4", 4578, "CREDITO");
        Transaccion trx5 = new Transaccion("5", 550, "DEBITO");

        List<Transaccion> listaTransacciones = List.of(trx1, trx2, trx3, trx4, trx5);

        for (Transaccion trx : listaTransacciones){
            if ("DEBITO".equals(trx.getTipo())){
                System.out.println("Transacción: [" + trx.getId() + ", " + trx.getMonto() + ", " + trx.getTipo() + "]");
            }
        }
        System.out.println("Total de los montos = " + Transaccion.sumaMontos(listaTransacciones));
    }
}
