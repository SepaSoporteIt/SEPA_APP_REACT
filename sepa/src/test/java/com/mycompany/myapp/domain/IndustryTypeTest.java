package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class IndustryTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IndustryType.class);
        IndustryType industryType1 = new IndustryType();
        industryType1.setId(1L);
        IndustryType industryType2 = new IndustryType();
        industryType2.setId(industryType1.getId());
        assertThat(industryType1).isEqualTo(industryType2);
        industryType2.setId(2L);
        assertThat(industryType1).isNotEqualTo(industryType2);
        industryType1.setId(null);
        assertThat(industryType1).isNotEqualTo(industryType2);
    }
}
