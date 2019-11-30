package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SepaApp;
import com.mycompany.myapp.domain.IndustryType;
import com.mycompany.myapp.repository.IndustryTypeRepository;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link IndustryTypeResource} REST controller.
 */
@SpringBootTest(classes = SepaApp.class)
public class IndustryTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CIIU = "AAAAAAAAAA";
    private static final String UPDATED_CIIU = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private IndustryTypeRepository industryTypeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restIndustryTypeMockMvc;

    private IndustryType industryType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IndustryTypeResource industryTypeResource = new IndustryTypeResource(industryTypeRepository);
        this.restIndustryTypeMockMvc = MockMvcBuilders.standaloneSetup(industryTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IndustryType createEntity(EntityManager em) {
        IndustryType industryType = new IndustryType()
            .name(DEFAULT_NAME)
            .ciiu(DEFAULT_CIIU)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
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
            .ciiu(UPDATED_CIIU)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
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
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(industryType)))
            .andExpect(status().isCreated());

        // Validate the IndustryType in the database
        List<IndustryType> industryTypeList = industryTypeRepository.findAll();
        assertThat(industryTypeList).hasSize(databaseSizeBeforeCreate + 1);
        IndustryType testIndustryType = industryTypeList.get(industryTypeList.size() - 1);
        assertThat(testIndustryType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testIndustryType.getCiiu()).isEqualTo(DEFAULT_CIIU);
        assertThat(testIndustryType.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testIndustryType.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    public void createIndustryTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = industryTypeRepository.findAll().size();

        // Create the IndustryType with an existing ID
        industryType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIndustryTypeMockMvc.perform(post("/api/industry-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
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
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
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
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
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
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(industryType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].ciiu").value(hasItem(DEFAULT_CIIU)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getIndustryType() throws Exception {
        // Initialize the database
        industryTypeRepository.saveAndFlush(industryType);

        // Get the industryType
        restIndustryTypeMockMvc.perform(get("/api/industry-types/{id}", industryType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(industryType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.ciiu").value(DEFAULT_CIIU))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
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
            .ciiu(UPDATED_CIIU)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restIndustryTypeMockMvc.perform(put("/api/industry-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIndustryType)))
            .andExpect(status().isOk());

        // Validate the IndustryType in the database
        List<IndustryType> industryTypeList = industryTypeRepository.findAll();
        assertThat(industryTypeList).hasSize(databaseSizeBeforeUpdate);
        IndustryType testIndustryType = industryTypeList.get(industryTypeList.size() - 1);
        assertThat(testIndustryType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testIndustryType.getCiiu()).isEqualTo(UPDATED_CIIU);
        assertThat(testIndustryType.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testIndustryType.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingIndustryType() throws Exception {
        int databaseSizeBeforeUpdate = industryTypeRepository.findAll().size();

        // Create the IndustryType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndustryTypeMockMvc.perform(put("/api/industry-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
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
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IndustryType> industryTypeList = industryTypeRepository.findAll();
        assertThat(industryTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
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
