package dev.doris.sesion3;
import dev.doris.diagnostico.Transaccion;

import java.util.ArrayList;
import java.util.List;

public class ListArrayListForEach {
    public static void main(String[] args) {
        List<Transaccion> lista = new ArrayList<Transaccion>();
        lista.add(new Transaccion("1", 120, "DEBITO"));
        lista.add(new Transaccion("2", 11200, "CREDITO"));
        lista.add(new Transaccion("3", 5170, "CREDITO"));
        lista.add(new Transaccion("4", 1000, "DEBITO"));

        System.out.println("Tamaño de la lista: " + lista.size()); //Salida: Tamaño de la lista: 4

        //Salida esperada 2 Objeto tipo Transaccion
        //Transacciones de Crédito: [2, 11200.0, CREDITO]
        //Transacciones de Crédito: [3, 5170.0, CREDITO]
        double sumaCreditos = 0;
        for (Transaccion trx : lista) {
            if ("CREDITO".equals(trx.getTipo())) {
                sumaCreditos += trx.getMonto();
                System.out.println("Transacciones de Crédito: " +
                        "[" + trx.getId() + ", " + trx.getMonto() + ", " + trx.getTipo() + "]");
            }
        }
        System.out.println("Total de los Créditos = " + sumaCreditos); //Salida: 16370.0

        //Salida esperada 2 Objeto tipo Transaccion
        //Transacciones de Débito: [1, 120.0, DEBITO]
        //Transacciones de Débito: [4, 1000.0, DEBITO]
        double sumaDebitos = 0;
        for (int i = 0; i < lista.size(); i++) {
            Transaccion elemento = lista.get(i);
            if ("DEBITO".equals(elemento.getTipo())) {
                sumaDebitos += elemento.getMonto();
                System.out.println("Transacciones de Débito: " +
                        "[" + elemento.getId() + ", " + elemento.getMonto() + ", " + elemento.getTipo() + "]");
            }
        }
        System.out.println("Total de los Débitos = " + sumaDebitos); //Salida: 1120.0
    }
}