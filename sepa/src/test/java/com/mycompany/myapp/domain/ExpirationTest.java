package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class ExpirationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Expiration.class);
        Expiration expiration1 = new Expiration();
        expiration1.setId(1L);
        Expiration expiration2 = new Expiration();
        expiration2.setId(expiration1.getId());
        assertThat(expiration1).isEqualTo(expiration2);
        expiration2.setId(2L);
        assertThat(expiration1).isNotEqualTo(expiration2);
        expiration1.setId(null);
        assertThat(expiration1).isNotEqualTo(expiration2);
    }
}
