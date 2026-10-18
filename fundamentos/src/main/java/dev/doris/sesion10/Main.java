package dev.doris.sesion10;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        BigDecimal saldo = new BigDecimal("100.00");
        // Salida: Nuevo Saldo: 130.00
        System.out.println("Nuevo Saldo: " + Depositar.depositar("0001", saldo, new BigDecimal("30.00"), new BigDecimal("200.00")));
        // Salida: Límite excedido. Exceso $20.00
        //         Exceso: 20.00
        try {
            System.out.println(Depositar.depositar("0001", saldo, new BigDecimal("120.00"), new BigDecimal("200.00")));
        } catch (LimiteExcedidoException error) {
            System.out.println(error.getMessage());
            System.out.println("Exceso: " + error.exceso());
        }
        // Salida: La cuenta 0000 está congelada.
        try {
            System.out.println(Depositar.depositar("0000", saldo, new BigDecimal("100.00"), new BigDecimal("200.00")));
        } catch (IllegalArgumentException error) {
            System.out.println("Cuenta Inválida.");
        } catch (CuentaCongeladaException error) {
            System.out.println(error.getMessage());
        }
        // Salida: El monto debe ser mayor a cero. Monto ingresado: null
        try {
            System.out.println(Depositar.depositar("0000", saldo, null, new BigDecimal("200.00")));
        } catch (IllegalArgumentException error) {
            System.out.println(error.getMessage());
        }
    }
}
