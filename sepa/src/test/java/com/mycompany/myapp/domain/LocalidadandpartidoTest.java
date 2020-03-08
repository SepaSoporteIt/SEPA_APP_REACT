package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class LocalidadandpartidoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Localidadandpartido.class);
        Localidadandpartido localidadandpartido1 = new Localidadandpartido();
        localidadandpartido1.setId(1L);
        Localidadandpartido localidadandpartido2 = new Localidadandpartido();
        localidadandpartido2.setId(localidadandpartido1.getId());
        assertThat(localidadandpartido1).isEqualTo(localidadandpartido2);
        localidadandpartido2.setId(2L);
        assertThat(localidadandpartido1).isNotEqualTo(localidadandpartido2);
        localidadandpartido1.setId(null);
        assertThat(localidadandpartido1).isNotEqualTo(localidadandpartido2);
    }
}
