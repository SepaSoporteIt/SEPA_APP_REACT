package com.app.sepa.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.app.sepa.web.rest.TestUtil;

public class LocalidadAndPartidoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LocalidadAndPartido.class);
        LocalidadAndPartido localidadAndPartido1 = new LocalidadAndPartido();
        localidadAndPartido1.setId(1L);
        LocalidadAndPartido localidadAndPartido2 = new LocalidadAndPartido();
        localidadAndPartido2.setId(localidadAndPartido1.getId());
        assertThat(localidadAndPartido1).isEqualTo(localidadAndPartido2);
        localidadAndPartido2.setId(2L);
        assertThat(localidadAndPartido1).isNotEqualTo(localidadAndPartido2);
        localidadAndPartido1.setId(null);
        assertThat(localidadAndPartido1).isNotEqualTo(localidadAndPartido2);
    }
}
