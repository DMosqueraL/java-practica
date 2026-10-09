package dev.doris.sesion4;

public class Main1 {
    public static void main(String[] args){

        Cuenta c1 = new Cuenta("0001", "Doris");
        Cuenta c2 = new Cuenta("0001", "Doris");
        Cuenta c3 = new Cuenta("0002", "Doris");

        System.out.println("c1.equals(c2) = " + c1.equals(c2)); //false
        System.out.println("c1 == c2 = " + (c1 == c2)); //true
        System.out.println("c1.equals(c3) = " + c1.equals(c3)); //false
        System.out.println("c1: " + c1); //Imprime algo así: Cuenta@1bjuikd
        System.out.println("c1.titular(): " + c1.titular()); //Doris
    }
}
