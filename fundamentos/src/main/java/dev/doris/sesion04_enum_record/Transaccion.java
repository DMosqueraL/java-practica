package dev.doris.sesion04_enum_record;

public record Transaccion(String id, double monto, TipoTransaccion tipo) {
}
