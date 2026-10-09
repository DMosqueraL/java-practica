package dev.doris.sesion5;

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
