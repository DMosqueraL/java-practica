package dev.doris.sesion4;

public class Main {
    public static void main(String[] args){

        //Predicción:
        //Nombre: PENDIENTE - Ordinal: 0
        //Nombre: APROBADA - Ordinal: 1
        //Nombre: RECHAZADA - Ordinal 2
        for (EstadoTransaccion estado : EstadoTransaccion.values()){
            System.out.println("Nombre: " + estado.name() + " - Ordinal: " + estado.ordinal());
        }
        //Predicción: true
        System.out.println(EstadoTransaccion.valueOf("RECHAZADA") == EstadoTransaccion.RECHAZADA);

        //Predicción: Lanza una excepción IllegalArgumentException dado que aprobada en minúsculas no está en el enum
        EstadoTransaccion.valueOf("aprobada");
    }
}
