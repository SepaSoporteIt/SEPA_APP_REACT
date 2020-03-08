package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SepaApp;
import com.mycompany.myapp.domain.Localidadandpartido;
import com.mycompany.myapp.repository.LocalidadandpartidoRepository;
import com.mycompany.myapp.repository.search.LocalidadandpartidoSearchRepository;
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
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
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
 * Integration tests for the {@link LocalidadandpartidoResource} REST controller.
 */
@SpringBootTest(classes = SepaApp.class)
public class LocalidadandpartidoResourceIT {

    private static final String DEFAULT_LOCALIDAD = "AAAAAAAAAA";
    private static final String UPDATED_LOCALIDAD = "BBBBBBBBBB";

    private static final String DEFAULT_PARTIDO = "AAAAAAAAAA";
    private static final String UPDATED_PARTIDO = "BBBBBBBBBB";

    @Autowired
    private LocalidadandpartidoRepository localidadandpartidoRepository;

    /**
     * This repository is mocked in the com.mycompany.myapp.repository.search test package.
     *
     * @see com.mycompany.myapp.repository.search.LocalidadandpartidoSearchRepositoryMockConfiguration
     */
    @Autowired
    private LocalidadandpartidoSearchRepository mockLocalidadandpartidoSearchRepository;

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

    private MockMvc restLocalidadandpartidoMockMvc;

    private Localidadandpartido localidadandpartido;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LocalidadandpartidoResource localidadandpartidoResource = new LocalidadandpartidoResource(localidadandpartidoRepository, mockLocalidadandpartidoSearchRepository);
        this.restLocalidadandpartidoMockMvc = MockMvcBuilders.standaloneSetup(localidadandpartidoResource)
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
    public static Localidadandpartido createEntity(EntityManager em) {
        Localidadandpartido localidadandpartido = new Localidadandpartido()
            .localidad(DEFAULT_LOCALIDAD)
            .partido(DEFAULT_PARTIDO);
        return localidadandpartido;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Localidadandpartido createUpdatedEntity(EntityManager em) {
        Localidadandpartido localidadandpartido = new Localidadandpartido()
            .localidad(UPDATED_LOCALIDAD)
            .partido(UPDATED_PARTIDO);
        return localidadandpartido;
    }

    @BeforeEach
    public void initTest() {
        localidadandpartido = createEntity(em);
    }

    @Test
    @Transactional
    public void createLocalidadandpartido() throws Exception {
        int databaseSizeBeforeCreate = localidadandpartidoRepository.findAll().size();

        // Create the Localidadandpartido
        restLocalidadandpartidoMockMvc.perform(post("/api/localidadandpartidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localidadandpartido)))
            .andExpect(status().isCreated());

        // Validate the Localidadandpartido in the database
        List<Localidadandpartido> localidadandpartidoList = localidadandpartidoRepository.findAll();
        assertThat(localidadandpartidoList).hasSize(databaseSizeBeforeCreate + 1);
        Localidadandpartido testLocalidadandpartido = localidadandpartidoList.get(localidadandpartidoList.size() - 1);
        assertThat(testLocalidadandpartido.getLocalidad()).isEqualTo(DEFAULT_LOCALIDAD);
        assertThat(testLocalidadandpartido.getPartido()).isEqualTo(DEFAULT_PARTIDO);

        // Validate the Localidadandpartido in Elasticsearch
        verify(mockLocalidadandpartidoSearchRepository, times(1)).save(testLocalidadandpartido);
    }

    @Test
    @Transactional
    public void createLocalidadandpartidoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = localidadandpartidoRepository.findAll().size();

        // Create the Localidadandpartido with an existing ID
        localidadandpartido.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocalidadandpartidoMockMvc.perform(post("/api/localidadandpartidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localidadandpartido)))
            .andExpect(status().isBadRequest());

        // Validate the Localidadandpartido in the database
        List<Localidadandpartido> localidadandpartidoList = localidadandpartidoRepository.findAll();
        assertThat(localidadandpartidoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Localidadandpartido in Elasticsearch
        verify(mockLocalidadandpartidoSearchRepository, times(0)).save(localidadandpartido);
    }


    @Test
    @Transactional
    public void checkLocalidadIsRequired() throws Exception {
        int databaseSizeBeforeTest = localidadandpartidoRepository.findAll().size();
        // set the field null
        localidadandpartido.setLocalidad(null);

        // Create the Localidadandpartido, which fails.

        restLocalidadandpartidoMockMvc.perform(post("/api/localidadandpartidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localidadandpartido)))
            .andExpect(status().isBadRequest());

        List<Localidadandpartido> localidadandpartidoList = localidadandpartidoRepository.findAll();
        assertThat(localidadandpartidoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPartidoIsRequired() throws Exception {
        int databaseSizeBeforeTest = localidadandpartidoRepository.findAll().size();
        // set the field null
        localidadandpartido.setPartido(null);

        // Create the Localidadandpartido, which fails.

        restLocalidadandpartidoMockMvc.perform(post("/api/localidadandpartidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localidadandpartido)))
            .andExpect(status().isBadRequest());

        List<Localidadandpartido> localidadandpartidoList = localidadandpartidoRepository.findAll();
        assertThat(localidadandpartidoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLocalidadandpartidos() throws Exception {
        // Initialize the database
        localidadandpartidoRepository.saveAndFlush(localidadandpartido);

        // Get all the localidadandpartidoList
        restLocalidadandpartidoMockMvc.perform(get("/api/localidadandpartidos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(localidadandpartido.getId().intValue())))
            .andExpect(jsonPath("$.[*].localidad").value(hasItem(DEFAULT_LOCALIDAD)))
            .andExpect(jsonPath("$.[*].partido").value(hasItem(DEFAULT_PARTIDO)));
    }
    
    @Test
    @Transactional
    public void getLocalidadandpartido() throws Exception {
        // Initialize the database
        localidadandpartidoRepository.saveAndFlush(localidadandpartido);

        // Get the localidadandpartido
        restLocalidadandpartidoMockMvc.perform(get("/api/localidadandpartidos/{id}", localidadandpartido.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(localidadandpartido.getId().intValue()))
            .andExpect(jsonPath("$.localidad").value(DEFAULT_LOCALIDAD))
            .andExpect(jsonPath("$.partido").value(DEFAULT_PARTIDO));
    }

    @Test
    @Transactional
    public void getNonExistingLocalidadandpartido() throws Exception {
        // Get the localidadandpartido
        restLocalidadandpartidoMockMvc.perform(get("/api/localidadandpartidos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLocalidadandpartido() throws Exception {
        // Initialize the database
        localidadandpartidoRepository.saveAndFlush(localidadandpartido);

        int databaseSizeBeforeUpdate = localidadandpartidoRepository.findAll().size();

        // Update the localidadandpartido
        Localidadandpartido updatedLocalidadandpartido = localidadandpartidoRepository.findById(localidadandpartido.getId()).get();
        // Disconnect from session so that the updates on updatedLocalidadandpartido are not directly saved in db
        em.detach(updatedLocalidadandpartido);
        updatedLocalidadandpartido
            .localidad(UPDATED_LOCALIDAD)
            .partido(UPDATED_PARTIDO);

        restLocalidadandpartidoMockMvc.perform(put("/api/localidadandpartidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLocalidadandpartido)))
            .andExpect(status().isOk());

        // Validate the Localidadandpartido in the database
        List<Localidadandpartido> localidadandpartidoList = localidadandpartidoRepository.findAll();
        assertThat(localidadandpartidoList).hasSize(databaseSizeBeforeUpdate);
        Localidadandpartido testLocalidadandpartido = localidadandpartidoList.get(localidadandpartidoList.size() - 1);
        assertThat(testLocalidadandpartido.getLocalidad()).isEqualTo(UPDATED_LOCALIDAD);
        assertThat(testLocalidadandpartido.getPartido()).isEqualTo(UPDATED_PARTIDO);

        // Validate the Localidadandpartido in Elasticsearch
        verify(mockLocalidadandpartidoSearchRepository, times(1)).save(testLocalidadandpartido);
    }

    @Test
    @Transactional
    public void updateNonExistingLocalidadandpartido() throws Exception {
        int databaseSizeBeforeUpdate = localidadandpartidoRepository.findAll().size();

        // Create the Localidadandpartido

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocalidadandpartidoMockMvc.perform(put("/api/localidadandpartidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localidadandpartido)))
            .andExpect(status().isBadRequest());

        // Validate the Localidadandpartido in the database
        List<Localidadandpartido> localidadandpartidoList = localidadandpartidoRepository.findAll();
        assertThat(localidadandpartidoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Localidadandpartido in Elasticsearch
        verify(mockLocalidadandpartidoSearchRepository, times(0)).save(localidadandpartido);
    }

    @Test
    @Transactional
    public void deleteLocalidadandpartido() throws Exception {
        // Initialize the database
        localidadandpartidoRepository.saveAndFlush(localidadandpartido);

        int databaseSizeBeforeDelete = localidadandpartidoRepository.findAll().size();

        // Delete the localidadandpartido
        restLocalidadandpartidoMockMvc.perform(delete("/api/localidadandpartidos/{id}", localidadandpartido.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Localidadandpartido> localidadandpartidoList = localidadandpartidoRepository.findAll();
        assertThat(localidadandpartidoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Localidadandpartido in Elasticsearch
        verify(mockLocalidadandpartidoSearchRepository, times(1)).deleteById(localidadandpartido.getId());
    }

    @Test
    @Transactional
    public void searchLocalidadandpartido() throws Exception {
        // Initialize the database
        localidadandpartidoRepository.saveAndFlush(localidadandpartido);
        when(mockLocalidadandpartidoSearchRepository.search(queryStringQuery("id:" + localidadandpartido.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(localidadandpartido), PageRequest.of(0, 1), 1));
        // Search the localidadandpartido
        restLocalidadandpartidoMockMvc.perform(get("/api/_search/localidadandpartidos?query=id:" + localidadandpartido.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(localidadandpartido.getId().intValue())))
            .andExpect(jsonPath("$.[*].localidad").value(hasItem(DEFAULT_LOCALIDAD)))
            .andExpect(jsonPath("$.[*].partido").value(hasItem(DEFAULT_PARTIDO)));
    }
}
