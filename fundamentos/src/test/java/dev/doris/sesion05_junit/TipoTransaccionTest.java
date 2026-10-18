package dev.doris.sesion05_junit;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TipoTransaccionTest {

    @Test
    public void valueOfConNombreExactoDevuelveLaConstante(){
        assertThat(TipoTransaccion.valueOf("DEBITO")).isSameAs(TipoTransaccion.DEBITO);
    }

    @Test
    public void valueOfConNombreInexistenteLanzaIllrgalArgumentException(){
        assertThatThrownBy(() -> TipoTransaccion.valueOf("PRESTAMO")).isInstanceOf(IllegalArgumentException.class);
    }
}
