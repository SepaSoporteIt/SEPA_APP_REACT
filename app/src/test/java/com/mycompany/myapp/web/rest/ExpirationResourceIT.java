package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SepaApp;
import com.mycompany.myapp.domain.Expiration;
import com.mycompany.myapp.repository.ExpirationRepository;
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
 * Integration tests for the {@link ExpirationResource} REST controller.
 */
@SpringBootTest(classes = SepaApp.class)
public class ExpirationResourceIT {

    private static final Integer DEFAULT_COMPANY_ID = 1;
    private static final Integer UPDATED_COMPANY_ID = 2;

    private static final Integer DEFAULT_EMPLOYEE_ID = 1;
    private static final Integer UPDATED_EMPLOYEE_ID = 2;

    private static final Integer DEFAULT_STUDY_ID = 1;
    private static final Integer UPDATED_STUDY_ID = 2;

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Long DEFAULT_COMMENTS = 1L;
    private static final Long UPDATED_COMMENTS = 2L;

    @Autowired
    private ExpirationRepository expirationRepository;

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

    private MockMvc restExpirationMockMvc;

    private Expiration expiration;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExpirationResource expirationResource = new ExpirationResource(expirationRepository);
        this.restExpirationMockMvc = MockMvcBuilders.standaloneSetup(expirationResource)
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
    public static Expiration createEntity(EntityManager em) {
        Expiration expiration = new Expiration()
            .companyId(DEFAULT_COMPANY_ID)
            .employeeId(DEFAULT_EMPLOYEE_ID)
            .studyId(DEFAULT_STUDY_ID)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .status(DEFAULT_STATUS)
            .comments(DEFAULT_COMMENTS);
        return expiration;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Expiration createUpdatedEntity(EntityManager em) {
        Expiration expiration = new Expiration()
            .companyId(UPDATED_COMPANY_ID)
            .employeeId(UPDATED_EMPLOYEE_ID)
            .studyId(UPDATED_STUDY_ID)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .status(UPDATED_STATUS)
            .comments(UPDATED_COMMENTS);
        return expiration;
    }

    @BeforeEach
    public void initTest() {
        expiration = createEntity(em);
    }

    @Test
    @Transactional
    public void createExpiration() throws Exception {
        int databaseSizeBeforeCreate = expirationRepository.findAll().size();

        // Create the Expiration
        restExpirationMockMvc.perform(post("/api/expirations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(expiration)))
            .andExpect(status().isCreated());

        // Validate the Expiration in the database
        List<Expiration> expirationList = expirationRepository.findAll();
        assertThat(expirationList).hasSize(databaseSizeBeforeCreate + 1);
        Expiration testExpiration = expirationList.get(expirationList.size() - 1);
        assertThat(testExpiration.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testExpiration.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
        assertThat(testExpiration.getStudyId()).isEqualTo(DEFAULT_STUDY_ID);
        assertThat(testExpiration.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testExpiration.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testExpiration.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testExpiration.getComments()).isEqualTo(DEFAULT_COMMENTS);
    }

    @Test
    @Transactional
    public void createExpirationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = expirationRepository.findAll().size();

        // Create the Expiration with an existing ID
        expiration.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExpirationMockMvc.perform(post("/api/expirations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(expiration)))
            .andExpect(status().isBadRequest());

        // Validate the Expiration in the database
        List<Expiration> expirationList = expirationRepository.findAll();
        assertThat(expirationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllExpirations() throws Exception {
        // Initialize the database
        expirationRepository.saveAndFlush(expiration);

        // Get all the expirationList
        restExpirationMockMvc.perform(get("/api/expirations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(expiration.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID)))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID)))
            .andExpect(jsonPath("$.[*].studyId").value(hasItem(DEFAULT_STUDY_ID)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.intValue())));
    }
    
    @Test
    @Transactional
    public void getExpiration() throws Exception {
        // Initialize the database
        expirationRepository.saveAndFlush(expiration);

        // Get the expiration
        restExpirationMockMvc.perform(get("/api/expirations/{id}", expiration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(expiration.getId().intValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID))
            .andExpect(jsonPath("$.employeeId").value(DEFAULT_EMPLOYEE_ID))
            .andExpect(jsonPath("$.studyId").value(DEFAULT_STUDY_ID))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingExpiration() throws Exception {
        // Get the expiration
        restExpirationMockMvc.perform(get("/api/expirations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExpiration() throws Exception {
        // Initialize the database
        expirationRepository.saveAndFlush(expiration);

        int databaseSizeBeforeUpdate = expirationRepository.findAll().size();

        // Update the expiration
        Expiration updatedExpiration = expirationRepository.findById(expiration.getId()).get();
        // Disconnect from session so that the updates on updatedExpiration are not directly saved in db
        em.detach(updatedExpiration);
        updatedExpiration
            .companyId(UPDATED_COMPANY_ID)
            .employeeId(UPDATED_EMPLOYEE_ID)
            .studyId(UPDATED_STUDY_ID)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .status(UPDATED_STATUS)
            .comments(UPDATED_COMMENTS);

        restExpirationMockMvc.perform(put("/api/expirations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedExpiration)))
            .andExpect(status().isOk());

        // Validate the Expiration in the database
        List<Expiration> expirationList = expirationRepository.findAll();
        assertThat(expirationList).hasSize(databaseSizeBeforeUpdate);
        Expiration testExpiration = expirationList.get(expirationList.size() - 1);
        assertThat(testExpiration.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testExpiration.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testExpiration.getStudyId()).isEqualTo(UPDATED_STUDY_ID);
        assertThat(testExpiration.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testExpiration.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testExpiration.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testExpiration.getComments()).isEqualTo(UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void updateNonExistingExpiration() throws Exception {
        int databaseSizeBeforeUpdate = expirationRepository.findAll().size();

        // Create the Expiration

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExpirationMockMvc.perform(put("/api/expirations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(expiration)))
            .andExpect(status().isBadRequest());

        // Validate the Expiration in the database
        List<Expiration> expirationList = expirationRepository.findAll();
        assertThat(expirationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExpiration() throws Exception {
        // Initialize the database
        expirationRepository.saveAndFlush(expiration);

        int databaseSizeBeforeDelete = expirationRepository.findAll().size();

        // Delete the expiration
        restExpirationMockMvc.perform(delete("/api/expirations/{id}", expiration.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Expiration> expirationList = expirationRepository.findAll();
        assertThat(expirationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
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
