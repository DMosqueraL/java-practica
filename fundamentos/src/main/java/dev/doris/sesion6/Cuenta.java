package dev.doris.sesion6;

import java.util.Objects;

public class Cuenta {
    private String numero;
    private String titular;

    public Cuenta(String numero, String titular){
        this.numero = numero;
        this.titular = titular;
    }

    public String getNumero() {
        return numero;
    }

    public String getTitular() {
        return titular;
    }

    @Override
    public boolean equals(Object o){
        if(o == null || getClass() != o.getClass()) return false;
        Cuenta otro = (Cuenta) o;
        return numero.equals(otro.numero);
    }

    @Override
    public int hashCode(){
        return Objects.hash(numero);
    }
}
