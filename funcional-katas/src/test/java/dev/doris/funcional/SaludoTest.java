package dev.doris.funcional;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SaludoTest {

    @Test
    void elEntornoSoportaRecordPatternsDeJava21() {
        assertThat(Saludo.texto(new Saludo.Formal("Doris"))).isEqualTo("Buenas noches, Doris");
        assertThat(Saludo.texto(new Saludo.Casual("Doris"))).isEqualTo("Hola, Doris");
    }
}
