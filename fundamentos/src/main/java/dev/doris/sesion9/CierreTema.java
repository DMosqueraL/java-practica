package dev.doris.sesion9;

public class CierreTema {

    static int dividir(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("b no puede ser 0");
        }
        return a / b;
    }

    static int repartir(int total, int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n invalido: " + n);
        }
        return total / n;
    }

    static int cuota(int total, int cuotas) {
        return repartir(total, cuotas);
    }

    static int largo(String texto) {
        return texto.length();
    }

    public static void main(String[] args) {
        System.out.println(dividir(10, 2));                       // [1]

        try {
            System.out.println("A");
            System.out.println(dividir(10, 0));
            System.out.println("B");                              // [2]
        } catch (IllegalArgumentException e) {
            System.out.println("C " + e.getMessage());            // [3]
        }
        System.out.println("D");                                  // [4]

        try {
            System.out.println(cuota(100, -1));
        } catch (IllegalArgumentException e) {
            System.out.println("E " + e.getMessage());            // [5]
        }

        try {
            System.out.println("F");
        } catch (IllegalArgumentException e) {
            System.out.println("G");                              // [6]
        }
        System.out.println("H");

        try {
            System.out.println(largo(null));                      // [7]
        } catch (IllegalArgumentException e) {
            System.out.println("I");
        }
        System.out.println("J");                                  // [8]
    }
}
