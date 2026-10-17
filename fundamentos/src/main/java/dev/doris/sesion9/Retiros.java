package dev.doris.sesion9;

public class Retiros {
    public static int restar(int saldo, int retiro) {
        if (retiro <= 0) {
            throw new IllegalArgumentException("El valor del retiro ingresado: $" + retiro + ", no puede ser cero o negativo.");
        }
        if (retiro > saldo) {
            throw new IllegalArgumentException("El valor del retiro: $" + retiro + ", no debe ser mayor al valor del saldo: $" + saldo);
        }
        return saldo - retiro;
    }
}
