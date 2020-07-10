package com.app.sepa.web.rest;

import com.app.sepa.SepaAppReactApp;
import com.app.sepa.domain.Study;
import com.app.sepa.repository.StudyRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link StudyResource} REST controller.
 */
@SpringBootTest(classes = SepaAppReactApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class StudyResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_RESOLUTION = "AAAAAAAAAA";
    private static final String UPDATED_RESOLUTION = "BBBBBBBBBB";

    private static final String DEFAULT_LEGISLATION = "AAAAAAAAAA";
    private static final String UPDATED_LEGISLATION = "BBBBBBBBBB";

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStudyMockMvc;

    private Study study;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Study createEntity(EntityManager em) {
        Study study = new Study()
            .name(DEFAULT_NAME)
            .resolution(DEFAULT_RESOLUTION)
            .legislation(DEFAULT_LEGISLATION);
        return study;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Study createUpdatedEntity(EntityManager em) {
        Study study = new Study()
            .name(UPDATED_NAME)
            .resolution(UPDATED_RESOLUTION)
            .legislation(UPDATED_LEGISLATION);
        return study;
    }

    @BeforeEach
    public void initTest() {
        study = createEntity(em);
    }

    @Test
    @Transactional
    public void createStudy() throws Exception {
        int databaseSizeBeforeCreate = studyRepository.findAll().size();
        // Create the Study
        restStudyMockMvc.perform(post("/api/studies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(study)))
            .andExpect(status().isCreated());

        // Validate the Study in the database
        List<Study> studyList = studyRepository.findAll();
        assertThat(studyList).hasSize(databaseSizeBeforeCreate + 1);
        Study testStudy = studyList.get(studyList.size() - 1);
        assertThat(testStudy.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStudy.getResolution()).isEqualTo(DEFAULT_RESOLUTION);
        assertThat(testStudy.getLegislation()).isEqualTo(DEFAULT_LEGISLATION);
    }

    @Test
    @Transactional
    public void createStudyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = studyRepository.findAll().size();

        // Create the Study with an existing ID
        study.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudyMockMvc.perform(post("/api/studies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(study)))
            .andExpect(status().isBadRequest());

        // Validate the Study in the database
        List<Study> studyList = studyRepository.findAll();
        assertThat(studyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = studyRepository.findAll().size();
        // set the field null
        study.setName(null);

        // Create the Study, which fails.


        restStudyMockMvc.perform(post("/api/studies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(study)))
            .andExpect(status().isBadRequest());

        List<Study> studyList = studyRepository.findAll();
        assertThat(studyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkResolutionIsRequired() throws Exception {
        int databaseSizeBeforeTest = studyRepository.findAll().size();
        // set the field null
        study.setResolution(null);

        // Create the Study, which fails.


        restStudyMockMvc.perform(post("/api/studies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(study)))
            .andExpect(status().isBadRequest());

        List<Study> studyList = studyRepository.findAll();
        assertThat(studyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLegislationIsRequired() throws Exception {
        int databaseSizeBeforeTest = studyRepository.findAll().size();
        // set the field null
        study.setLegislation(null);

        // Create the Study, which fails.


        restStudyMockMvc.perform(post("/api/studies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(study)))
            .andExpect(status().isBadRequest());

        List<Study> studyList = studyRepository.findAll();
        assertThat(studyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStudies() throws Exception {
        // Initialize the database
        studyRepository.saveAndFlush(study);

        // Get all the studyList
        restStudyMockMvc.perform(get("/api/studies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(study.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].resolution").value(hasItem(DEFAULT_RESOLUTION)))
            .andExpect(jsonPath("$.[*].legislation").value(hasItem(DEFAULT_LEGISLATION)));
    }
    
    @Test
    @Transactional
    public void getStudy() throws Exception {
        // Initialize the database
        studyRepository.saveAndFlush(study);

        // Get the study
        restStudyMockMvc.perform(get("/api/studies/{id}", study.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(study.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.resolution").value(DEFAULT_RESOLUTION))
            .andExpect(jsonPath("$.legislation").value(DEFAULT_LEGISLATION));
    }
    @Test
    @Transactional
    public void getNonExistingStudy() throws Exception {
        // Get the study
        restStudyMockMvc.perform(get("/api/studies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudy() throws Exception {
        // Initialize the database
        studyRepository.saveAndFlush(study);

        int databaseSizeBeforeUpdate = studyRepository.findAll().size();

        // Update the study
        Study updatedStudy = studyRepository.findById(study.getId()).get();
        // Disconnect from session so that the updates on updatedStudy are not directly saved in db
        em.detach(updatedStudy);
        updatedStudy
            .name(UPDATED_NAME)
            .resolution(UPDATED_RESOLUTION)
            .legislation(UPDATED_LEGISLATION);

        restStudyMockMvc.perform(put("/api/studies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedStudy)))
            .andExpect(status().isOk());

        // Validate the Study in the database
        List<Study> studyList = studyRepository.findAll();
        assertThat(studyList).hasSize(databaseSizeBeforeUpdate);
        Study testStudy = studyList.get(studyList.size() - 1);
        assertThat(testStudy.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStudy.getResolution()).isEqualTo(UPDATED_RESOLUTION);
        assertThat(testStudy.getLegislation()).isEqualTo(UPDATED_LEGISLATION);
    }

    @Test
    @Transactional
    public void updateNonExistingStudy() throws Exception {
        int databaseSizeBeforeUpdate = studyRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudyMockMvc.perform(put("/api/studies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(study)))
            .andExpect(status().isBadRequest());

        // Validate the Study in the database
        List<Study> studyList = studyRepository.findAll();
        assertThat(studyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStudy() throws Exception {
        // Initialize the database
        studyRepository.saveAndFlush(study);

        int databaseSizeBeforeDelete = studyRepository.findAll().size();

        // Delete the study
        restStudyMockMvc.perform(delete("/api/studies/{id}", study.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Study> studyList = studyRepository.findAll();
        assertThat(studyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
