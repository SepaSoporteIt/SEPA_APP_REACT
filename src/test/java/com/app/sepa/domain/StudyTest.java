package com.app.sepa.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.app.sepa.web.rest.TestUtil;

public class StudyTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Study.class);
        Study study1 = new Study();
        study1.setId(1L);
        Study study2 = new Study();
        study2.setId(study1.getId());
        assertThat(study1).isEqualTo(study2);
        study2.setId(2L);
        assertThat(study1).isNotEqualTo(study2);
        study1.setId(null);
        assertThat(study1).isNotEqualTo(study2);
    }
}
