package com.app.sepa.web.rest;

import com.app.sepa.SepaApp;
import com.app.sepa.domain.IndustryType;
import com.app.sepa.repository.IndustryTypeRepository;

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
 * Integration tests for the {@link IndustryTypeResource} REST controller.
 */
@SpringBootTest(classes = SepaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class IndustryTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CIIU = "AAAAAAAAAA";
    private static final String UPDATED_CIIU = "BBBBBBBBBB";

    @Autowired
    private IndustryTypeRepository industryTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIndustryTypeMockMvc;

    private IndustryType industryType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IndustryType createEntity(EntityManager em) {
        IndustryType industryType = new IndustryType()
            .name(DEFAULT_NAME)
            .ciiu(DEFAULT_CIIU);
        return industryType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IndustryType createUpdatedEntity(EntityManager em) {
        IndustryType industryType = new IndustryType()
            .name(UPDATED_NAME)
            .ciiu(UPDATED_CIIU);
        return industryType;
    }

    @BeforeEach
    public void initTest() {
        industryType = createEntity(em);
    }

    @Test
    @Transactional
    public void createIndustryType() throws Exception {
        int databaseSizeBeforeCreate = industryTypeRepository.findAll().size();
        // Create the IndustryType
        restIndustryTypeMockMvc.perform(post("/api/industry-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(industryType)))
            .andExpect(status().isCreated());

        // Validate the IndustryType in the database
        List<IndustryType> industryTypeList = industryTypeRepository.findAll();
        assertThat(industryTypeList).hasSize(databaseSizeBeforeCreate + 1);
        IndustryType testIndustryType = industryTypeList.get(industryTypeList.size() - 1);
        assertThat(testIndustryType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testIndustryType.getCiiu()).isEqualTo(DEFAULT_CIIU);
    }

    @Test
    @Transactional
    public void createIndustryTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = industryTypeRepository.findAll().size();

        // Create the IndustryType with an existing ID
        industryType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIndustryTypeMockMvc.perform(post("/api/industry-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(industryType)))
            .andExpect(status().isBadRequest());

        // Validate the IndustryType in the database
        List<IndustryType> industryTypeList = industryTypeRepository.findAll();
        assertThat(industryTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = industryTypeRepository.findAll().size();
        // set the field null
        industryType.setName(null);

        // Create the IndustryType, which fails.


        restIndustryTypeMockMvc.perform(post("/api/industry-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(industryType)))
            .andExpect(status().isBadRequest());

        List<IndustryType> industryTypeList = industryTypeRepository.findAll();
        assertThat(industryTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCiiuIsRequired() throws Exception {
        int databaseSizeBeforeTest = industryTypeRepository.findAll().size();
        // set the field null
        industryType.setCiiu(null);

        // Create the IndustryType, which fails.


        restIndustryTypeMockMvc.perform(post("/api/industry-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(industryType)))
            .andExpect(status().isBadRequest());

        List<IndustryType> industryTypeList = industryTypeRepository.findAll();
        assertThat(industryTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIndustryTypes() throws Exception {
        // Initialize the database
        industryTypeRepository.saveAndFlush(industryType);

        // Get all the industryTypeList
        restIndustryTypeMockMvc.perform(get("/api/industry-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(industryType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].ciiu").value(hasItem(DEFAULT_CIIU)));
    }
    
    @Test
    @Transactional
    public void getIndustryType() throws Exception {
        // Initialize the database
        industryTypeRepository.saveAndFlush(industryType);

        // Get the industryType
        restIndustryTypeMockMvc.perform(get("/api/industry-types/{id}", industryType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(industryType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.ciiu").value(DEFAULT_CIIU));
    }
    @Test
    @Transactional
    public void getNonExistingIndustryType() throws Exception {
        // Get the industryType
        restIndustryTypeMockMvc.perform(get("/api/industry-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIndustryType() throws Exception {
        // Initialize the database
        industryTypeRepository.saveAndFlush(industryType);

        int databaseSizeBeforeUpdate = industryTypeRepository.findAll().size();

        // Update the industryType
        IndustryType updatedIndustryType = industryTypeRepository.findById(industryType.getId()).get();
        // Disconnect from session so that the updates on updatedIndustryType are not directly saved in db
        em.detach(updatedIndustryType);
        updatedIndustryType
            .name(UPDATED_NAME)
            .ciiu(UPDATED_CIIU);

        restIndustryTypeMockMvc.perform(put("/api/industry-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedIndustryType)))
            .andExpect(status().isOk());

        // Validate the IndustryType in the database
        List<IndustryType> industryTypeList = industryTypeRepository.findAll();
        assertThat(industryTypeList).hasSize(databaseSizeBeforeUpdate);
        IndustryType testIndustryType = industryTypeList.get(industryTypeList.size() - 1);
        assertThat(testIndustryType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testIndustryType.getCiiu()).isEqualTo(UPDATED_CIIU);
    }

    @Test
    @Transactional
    public void updateNonExistingIndustryType() throws Exception {
        int databaseSizeBeforeUpdate = industryTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndustryTypeMockMvc.perform(put("/api/industry-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(industryType)))
            .andExpect(status().isBadRequest());

        // Validate the IndustryType in the database
        List<IndustryType> industryTypeList = industryTypeRepository.findAll();
        assertThat(industryTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIndustryType() throws Exception {
        // Initialize the database
        industryTypeRepository.saveAndFlush(industryType);

        int databaseSizeBeforeDelete = industryTypeRepository.findAll().size();

        // Delete the industryType
        restIndustryTypeMockMvc.perform(delete("/api/industry-types/{id}", industryType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IndustryType> industryTypeList = industryTypeRepository.findAll();
        assertThat(industryTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
