package dev.doris.sesion2;

import dev.doris.diagnostico.Transaccion;

public class TiposYEquals {
    public static void main(String[] args) {
        int x = 7;
        int y = 7;
        System.out.println(x == y);              // true

        String a = "hola";
        String b = "hola";
        System.out.println(a == b);              // true

        String c = new String("hola");
        System.out.println(a == c);              // false
        System.out.println(a.equals(c));         // true

        Transaccion t1 = new Transaccion("1", 100, "DEBITO");
        Transaccion t2 = new Transaccion("1", 100, "DEBITO");
        Transaccion t3 = new Transaccion("1", 100, "CREDITO");
        System.out.println(t1 == t2);            // false
        System.out.println(t1.equals(t2));       // false
        System.out.println(t1.equals(t3));       // false
    }
}
