package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SepaApp;
import com.mycompany.myapp.domain.Expiration;
import com.mycompany.myapp.repository.ExpirationRepository;
import com.mycompany.myapp.repository.search.ExpirationSearchRepository;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ExpirationResource} REST controller.
 */
@SpringBootTest(classes = SepaApp.class)
public class ExpirationResourceIT {

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    @Autowired
    private ExpirationRepository expirationRepository;

    /**
     * This repository is mocked in the com.mycompany.myapp.repository.search test package.
     *
     * @see com.mycompany.myapp.repository.search.ExpirationSearchRepositoryMockConfiguration
     */
    @Autowired
    private ExpirationSearchRepository mockExpirationSearchRepository;

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
        final ExpirationResource expirationResource = new ExpirationResource(expirationRepository, mockExpirationSearchRepository);
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
        assertThat(testExpiration.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testExpiration.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testExpiration.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testExpiration.getComments()).isEqualTo(DEFAULT_COMMENTS);

        // Validate the Expiration in Elasticsearch
        verify(mockExpirationSearchRepository, times(1)).save(testExpiration);
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

        // Validate the Expiration in Elasticsearch
        verify(mockExpirationSearchRepository, times(0)).save(expiration);
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
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())));
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
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()));
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
        assertThat(testExpiration.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testExpiration.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testExpiration.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testExpiration.getComments()).isEqualTo(UPDATED_COMMENTS);

        // Validate the Expiration in Elasticsearch
        verify(mockExpirationSearchRepository, times(1)).save(testExpiration);
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

        // Validate the Expiration in Elasticsearch
        verify(mockExpirationSearchRepository, times(0)).save(expiration);
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

        // Validate the Expiration in Elasticsearch
        verify(mockExpirationSearchRepository, times(1)).deleteById(expiration.getId());
    }

    @Test
    @Transactional
    public void searchExpiration() throws Exception {
        // Initialize the database
        expirationRepository.saveAndFlush(expiration);
        when(mockExpirationSearchRepository.search(queryStringQuery("id:" + expiration.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(expiration), PageRequest.of(0, 1), 1));
        // Search the expiration
        restExpirationMockMvc.perform(get("/api/_search/expirations?query=id:" + expiration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(expiration.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())));
    }
}
