package dev.doris.diagnostico;

import java.util.List;

public class Main {

    //Predicción: "DEBITO".equals(trx.getTipo()) true cuando coincida con las transacciones
    //trx1, trx2 y trx4
    //Se imprimirá: Transacción: [1, 1500.0, DEBITO], Transacción: [2, 10.0, DEBITO], Transacción: [4, 2500.0, DEBITO]
    // Total de los montos = $4560.0 (viene con el .0 pq es tipo double
    public static void main(String[] args){
        Transaccion trx1 = new Transaccion("1", 1500, "DEBITO");
        Transaccion trx2 = new Transaccion("2", 10, "DEBITO");
        Transaccion trx3 = new Transaccion("3", 500, "CREDITO");
        Transaccion trx4 = new Transaccion("4", 2500, "DEBITO");
        Transaccion trx5 = new Transaccion("5", 50, "CREDITO");

        List<Transaccion> transacciones = List.of(trx1, trx2, trx3, trx4, trx5);

        for (Transaccion trx : transacciones){
            if ("DEBITO".equals(trx.getTipo())) {
                System.out.println("Transacción: [" + trx.getId() + ", " + trx.getMonto() + ", " + trx.getTipo() + "]");
            }
        }
        System.out.println("Total de los montos = $" + Transaccion.sumaMontos(transacciones));
    }
}
