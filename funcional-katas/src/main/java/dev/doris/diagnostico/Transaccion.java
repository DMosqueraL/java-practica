/**
 * Una clase con tres campos (id de tipo String, monto de tipo double, tipo de tipo String),
 * un constructor y getters.
 */

package dev.doris.diagnostico;

import java.util.List;

public class Transaccion {

    private String id;
    private double monto;
    private String tipo;

    public Transaccion(String id, double monto, String tipo) {
        this.id = id;
        this.monto = monto;
        this.tipo = tipo;
    }

    public String getId() {
        return id;
    }

    public double getMonto() {
        return monto;
    }

    public String getTipo() {
        return tipo;
    }

    public static double sumaMontos(List<Transaccion> lista) {
        double suma = 0;
        for (int i = 0; i < lista.size(); i++) {
            double elemento = lista.get(i).getMonto();
            suma += elemento;
        }
        return suma;
    }

    @Override
    public boolean equals(Object o) {
        // 1. ¿o es null, o de otra clase? → return false
        if (o == null || getClass() != o.getClass()) return false;

        // 2. cast a Transaccion
        Transaccion otra = (Transaccion) o;

        // 3. comparar id y tipo con equals
        return id.equals(otra.id) && tipo.equals(otra.tipo);
    }
}
