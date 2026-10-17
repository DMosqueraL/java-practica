package dev.doris.sesion9;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Total de los montos: $" + Abonos.sumarMontos(null)); // Salida: La lista no puede ser nula.
        } catch (IllegalArgumentException error) {
            System.out.println(error.getMessage());
        }

        try {
            System.out.println("Saldo disponible: $" + Retiros.restar(1000, -500)); // Salida: El valor del retiro ingresado: $-500, no puede ser cero o negativo.
        } catch (IllegalArgumentException error) {
            System.out.println(error.getMessage());
        }

        try {
            System.out.println("Saldo disponible: $" + Retiros.restar(1000, 2000)); // Salida: El valor del retiro: $2000, no debe ser mayor al valor del saldo: $1000"
        } catch (IllegalArgumentException error) {
            System.out.println(error.getMessage());
        }

        try {
            System.out.println("Saldo disponible: $" + Retiros.restar(1000, 400)); // Salida: Saldo disponible: $600
        } catch (IllegalArgumentException error) {
            System.out.println(error.getMessage());
        }
    }
}
