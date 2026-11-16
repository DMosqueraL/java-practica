package dev.doris.sesion18_formato;

import java.math.BigDecimal;

public class CierreDeTema {
    public static void main(String[] args) {
        System.out.println("[1] " + String.format("%s-%d", "A", 7));
        System.out.println("[2] " + String.format("[%6s]", "Luz"));
        System.out.println("[3] " + String.format("[%-6s]", "Luz"));
        System.out.println("[4] " + String.format("[%4d]", 12345));
        System.out.println("[5] " + String.format("[%8s]", BigDecimal.ZERO));
        System.out.println("[6] " + String.format("[%8s]", new BigDecimal("7.5").setScale(2)));
        System.out.println("[7] " + String.format("%-5s%5s", "Gas", "x").length());

        String texto = "Total";
        String.format("%10s", texto);
        System.out.println("[8] " + "[" + texto + "]");
    }
}
