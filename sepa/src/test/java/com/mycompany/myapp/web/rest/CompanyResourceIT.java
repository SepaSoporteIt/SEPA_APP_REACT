package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SepaApp;
import com.mycompany.myapp.domain.Company;
import com.mycompany.myapp.repository.CompanyRepository;
import com.mycompany.myapp.repository.search.CompanySearchRepository;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link CompanyResource} REST controller.
 */
@SpringBootTest(classes = SepaApp.class)
public class CompanyResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_DIRECTION = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_DIRECTION = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_FLOOR = "AAAAAAAAAA";
    private static final String UPDATED_FLOOR = "BBBBBBBBBB";

    private static final String DEFAULT_DEPARTAMENT = "AAAAAAAAAA";
    private static final String UPDATED_DEPARTAMENT = "BBBBBBBBBB";

    private static final String DEFAULT_CUIT = "AAAAAAAAAA";
    private static final String UPDATED_CUIT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_SUBSCRIPTED = false;
    private static final Boolean UPDATED_IS_SUBSCRIPTED = true;

    private static final String DEFAULT_FANTASY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FANTASY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_TLF = "AAAAAAAAAA";
    private static final String UPDATED_TLF = "BBBBBBBBBB";

    private static final String DEFAULT_INTERNAL_TLF = "AAAAAAAAAA";
    private static final String UPDATED_INTERNAL_TLF = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT = "BBBBBBBBBB";

    private static final String DEFAULT_CELLPHONE = "AAAAAAAAAA";
    private static final String UPDATED_CELLPHONE = "BBBBBBBBBB";

    private static final Integer DEFAULT_VISITS_QTY_MIN = 1;
    private static final Integer UPDATED_VISITS_QTY_MIN = 2;

    private static final Integer DEFAULT_VISITS_QTY_MAX = 1;
    private static final Integer UPDATED_VISITS_QTY_MAX = 2;

    private static final String DEFAULT_HAB_PRIM = "AAAAAAAAAA";
    private static final String UPDATED_HAB_PRIM = "BBBBBBBBBB";

    private static final String DEFAULT_HAB_SEC = "AAAAAAAAAA";
    private static final String UPDATED_HAB_SEC = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final Integer DEFAULT_LEGISLATION_ID = 1;
    private static final Integer UPDATED_LEGISLATION_ID = 2;

    private static final Integer DEFAULT_SOLICITADOR_ID = 1;
    private static final Integer UPDATED_SOLICITADOR_ID = 2;

    private static final Integer DEFAULT_AMBITO_ID = 1;
    private static final Integer UPDATED_AMBITO_ID = 2;

    private static final Integer DEFAULT_AUTORIDAD_ID = 1;
    private static final Integer UPDATED_AUTORIDAD_ID = 2;

    private static final Integer DEFAULT_FRECUENCY_TYPE_ID = 1;
    private static final Integer UPDATED_FRECUENCY_TYPE_ID = 2;

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CompanyRepository companyRepository;

    /**
     * This repository is mocked in the com.mycompany.myapp.repository.search test package.
     *
     * @see com.mycompany.myapp.repository.search.CompanySearchRepositoryMockConfiguration
     */
    @Autowired
    private CompanySearchRepository mockCompanySearchRepository;

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

    private MockMvc restCompanyMockMvc;

    private Company company;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompanyResource companyResource = new CompanyResource(companyRepository, mockCompanySearchRepository);
        this.restCompanyMockMvc = MockMvcBuilders.standaloneSetup(companyResource)
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
    public static Company createEntity(EntityManager em) {
        Company company = new Company()
            .name(DEFAULT_NAME)
            .email(DEFAULT_EMAIL)
            .addressDirection(DEFAULT_ADDRESS_DIRECTION)
            .addressNumber(DEFAULT_ADDRESS_NUMBER)
            .floor(DEFAULT_FLOOR)
            .departament(DEFAULT_DEPARTAMENT)
            .cuit(DEFAULT_CUIT)
            .isSubscripted(DEFAULT_IS_SUBSCRIPTED)
            .fantasyName(DEFAULT_FANTASY_NAME)
            .postalCode(DEFAULT_POSTAL_CODE)
            .tlf(DEFAULT_TLF)
            .internalTlf(DEFAULT_INTERNAL_TLF)
            .contact(DEFAULT_CONTACT)
            .cellphone(DEFAULT_CELLPHONE)
            .visitsQtyMin(DEFAULT_VISITS_QTY_MIN)
            .visitsQtyMax(DEFAULT_VISITS_QTY_MAX)
            .habPrim(DEFAULT_HAB_PRIM)
            .habSec(DEFAULT_HAB_SEC)
            .comment(DEFAULT_COMMENT)
            .legislationId(DEFAULT_LEGISLATION_ID)
            .solicitadorId(DEFAULT_SOLICITADOR_ID)
            .ambitoId(DEFAULT_AMBITO_ID)
            .autoridadId(DEFAULT_AUTORIDAD_ID)
            .frecuencyTypeId(DEFAULT_FRECUENCY_TYPE_ID)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return company;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Company createUpdatedEntity(EntityManager em) {
        Company company = new Company()
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .addressDirection(UPDATED_ADDRESS_DIRECTION)
            .addressNumber(UPDATED_ADDRESS_NUMBER)
            .floor(UPDATED_FLOOR)
            .departament(UPDATED_DEPARTAMENT)
            .cuit(UPDATED_CUIT)
            .isSubscripted(UPDATED_IS_SUBSCRIPTED)
            .fantasyName(UPDATED_FANTASY_NAME)
            .postalCode(UPDATED_POSTAL_CODE)
            .tlf(UPDATED_TLF)
            .internalTlf(UPDATED_INTERNAL_TLF)
            .contact(UPDATED_CONTACT)
            .cellphone(UPDATED_CELLPHONE)
            .visitsQtyMin(UPDATED_VISITS_QTY_MIN)
            .visitsQtyMax(UPDATED_VISITS_QTY_MAX)
            .habPrim(UPDATED_HAB_PRIM)
            .habSec(UPDATED_HAB_SEC)
            .comment(UPDATED_COMMENT)
            .legislationId(UPDATED_LEGISLATION_ID)
            .solicitadorId(UPDATED_SOLICITADOR_ID)
            .ambitoId(UPDATED_AMBITO_ID)
            .autoridadId(UPDATED_AUTORIDAD_ID)
            .frecuencyTypeId(UPDATED_FRECUENCY_TYPE_ID)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        return company;
    }

    @BeforeEach
    public void initTest() {
        company = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompany() throws Exception {
        int databaseSizeBeforeCreate = companyRepository.findAll().size();

        // Create the Company
        restCompanyMockMvc.perform(post("/api/companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isCreated());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeCreate + 1);
        Company testCompany = companyList.get(companyList.size() - 1);
        assertThat(testCompany.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCompany.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCompany.getAddressDirection()).isEqualTo(DEFAULT_ADDRESS_DIRECTION);
        assertThat(testCompany.getAddressNumber()).isEqualTo(DEFAULT_ADDRESS_NUMBER);
        assertThat(testCompany.getFloor()).isEqualTo(DEFAULT_FLOOR);
        assertThat(testCompany.getDepartament()).isEqualTo(DEFAULT_DEPARTAMENT);
        assertThat(testCompany.getCuit()).isEqualTo(DEFAULT_CUIT);
        assertThat(testCompany.isIsSubscripted()).isEqualTo(DEFAULT_IS_SUBSCRIPTED);
        assertThat(testCompany.getFantasyName()).isEqualTo(DEFAULT_FANTASY_NAME);
        assertThat(testCompany.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testCompany.getTlf()).isEqualTo(DEFAULT_TLF);
        assertThat(testCompany.getInternalTlf()).isEqualTo(DEFAULT_INTERNAL_TLF);
        assertThat(testCompany.getContact()).isEqualTo(DEFAULT_CONTACT);
        assertThat(testCompany.getCellphone()).isEqualTo(DEFAULT_CELLPHONE);
        assertThat(testCompany.getVisitsQtyMin()).isEqualTo(DEFAULT_VISITS_QTY_MIN);
        assertThat(testCompany.getVisitsQtyMax()).isEqualTo(DEFAULT_VISITS_QTY_MAX);
        assertThat(testCompany.getHabPrim()).isEqualTo(DEFAULT_HAB_PRIM);
        assertThat(testCompany.getHabSec()).isEqualTo(DEFAULT_HAB_SEC);
        assertThat(testCompany.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testCompany.getLegislationId()).isEqualTo(DEFAULT_LEGISLATION_ID);
        assertThat(testCompany.getSolicitadorId()).isEqualTo(DEFAULT_SOLICITADOR_ID);
        assertThat(testCompany.getAmbitoId()).isEqualTo(DEFAULT_AMBITO_ID);
        assertThat(testCompany.getAutoridadId()).isEqualTo(DEFAULT_AUTORIDAD_ID);
        assertThat(testCompany.getFrecuencyTypeId()).isEqualTo(DEFAULT_FRECUENCY_TYPE_ID);
        assertThat(testCompany.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testCompany.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);

        // Validate the Company in Elasticsearch
        verify(mockCompanySearchRepository, times(1)).save(testCompany);
    }

    @Test
    @Transactional
    public void createCompanyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyRepository.findAll().size();

        // Create the Company with an existing ID
        company.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyMockMvc.perform(post("/api/companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isBadRequest());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeCreate);

        // Validate the Company in Elasticsearch
        verify(mockCompanySearchRepository, times(0)).save(company);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyRepository.findAll().size();
        // set the field null
        company.setName(null);

        // Create the Company, which fails.

        restCompanyMockMvc.perform(post("/api/companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isBadRequest());

        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAddressDirectionIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyRepository.findAll().size();
        // set the field null
        company.setAddressDirection(null);

        // Create the Company, which fails.

        restCompanyMockMvc.perform(post("/api/companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isBadRequest());

        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAddressNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyRepository.findAll().size();
        // set the field null
        company.setAddressNumber(null);

        // Create the Company, which fails.

        restCompanyMockMvc.perform(post("/api/companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isBadRequest());

        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCuitIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyRepository.findAll().size();
        // set the field null
        company.setCuit(null);

        // Create the Company, which fails.

        restCompanyMockMvc.perform(post("/api/companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isBadRequest());

        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCompanies() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList
        restCompanyMockMvc.perform(get("/api/companies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(company.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].addressDirection").value(hasItem(DEFAULT_ADDRESS_DIRECTION)))
            .andExpect(jsonPath("$.[*].addressNumber").value(hasItem(DEFAULT_ADDRESS_NUMBER)))
            .andExpect(jsonPath("$.[*].floor").value(hasItem(DEFAULT_FLOOR)))
            .andExpect(jsonPath("$.[*].departament").value(hasItem(DEFAULT_DEPARTAMENT)))
            .andExpect(jsonPath("$.[*].cuit").value(hasItem(DEFAULT_CUIT)))
            .andExpect(jsonPath("$.[*].isSubscripted").value(hasItem(DEFAULT_IS_SUBSCRIPTED.booleanValue())))
            .andExpect(jsonPath("$.[*].fantasyName").value(hasItem(DEFAULT_FANTASY_NAME)))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].tlf").value(hasItem(DEFAULT_TLF)))
            .andExpect(jsonPath("$.[*].internalTlf").value(hasItem(DEFAULT_INTERNAL_TLF)))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT)))
            .andExpect(jsonPath("$.[*].cellphone").value(hasItem(DEFAULT_CELLPHONE)))
            .andExpect(jsonPath("$.[*].visitsQtyMin").value(hasItem(DEFAULT_VISITS_QTY_MIN)))
            .andExpect(jsonPath("$.[*].visitsQtyMax").value(hasItem(DEFAULT_VISITS_QTY_MAX)))
            .andExpect(jsonPath("$.[*].habPrim").value(hasItem(DEFAULT_HAB_PRIM)))
            .andExpect(jsonPath("$.[*].habSec").value(hasItem(DEFAULT_HAB_SEC)))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].legislationId").value(hasItem(DEFAULT_LEGISLATION_ID)))
            .andExpect(jsonPath("$.[*].solicitadorId").value(hasItem(DEFAULT_SOLICITADOR_ID)))
            .andExpect(jsonPath("$.[*].ambitoId").value(hasItem(DEFAULT_AMBITO_ID)))
            .andExpect(jsonPath("$.[*].autoridadId").value(hasItem(DEFAULT_AUTORIDAD_ID)))
            .andExpect(jsonPath("$.[*].frecuencyTypeId").value(hasItem(DEFAULT_FRECUENCY_TYPE_ID)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getCompany() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get the company
        restCompanyMockMvc.perform(get("/api/companies/{id}", company.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(company.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.addressDirection").value(DEFAULT_ADDRESS_DIRECTION))
            .andExpect(jsonPath("$.addressNumber").value(DEFAULT_ADDRESS_NUMBER))
            .andExpect(jsonPath("$.floor").value(DEFAULT_FLOOR))
            .andExpect(jsonPath("$.departament").value(DEFAULT_DEPARTAMENT))
            .andExpect(jsonPath("$.cuit").value(DEFAULT_CUIT))
            .andExpect(jsonPath("$.isSubscripted").value(DEFAULT_IS_SUBSCRIPTED.booleanValue()))
            .andExpect(jsonPath("$.fantasyName").value(DEFAULT_FANTASY_NAME))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE))
            .andExpect(jsonPath("$.tlf").value(DEFAULT_TLF))
            .andExpect(jsonPath("$.internalTlf").value(DEFAULT_INTERNAL_TLF))
            .andExpect(jsonPath("$.contact").value(DEFAULT_CONTACT))
            .andExpect(jsonPath("$.cellphone").value(DEFAULT_CELLPHONE))
            .andExpect(jsonPath("$.visitsQtyMin").value(DEFAULT_VISITS_QTY_MIN))
            .andExpect(jsonPath("$.visitsQtyMax").value(DEFAULT_VISITS_QTY_MAX))
            .andExpect(jsonPath("$.habPrim").value(DEFAULT_HAB_PRIM))
            .andExpect(jsonPath("$.habSec").value(DEFAULT_HAB_SEC))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.legislationId").value(DEFAULT_LEGISLATION_ID))
            .andExpect(jsonPath("$.solicitadorId").value(DEFAULT_SOLICITADOR_ID))
            .andExpect(jsonPath("$.ambitoId").value(DEFAULT_AMBITO_ID))
            .andExpect(jsonPath("$.autoridadId").value(DEFAULT_AUTORIDAD_ID))
            .andExpect(jsonPath("$.frecuencyTypeId").value(DEFAULT_FRECUENCY_TYPE_ID))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCompany() throws Exception {
        // Get the company
        restCompanyMockMvc.perform(get("/api/companies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompany() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        int databaseSizeBeforeUpdate = companyRepository.findAll().size();

        // Update the company
        Company updatedCompany = companyRepository.findById(company.getId()).get();
        // Disconnect from session so that the updates on updatedCompany are not directly saved in db
        em.detach(updatedCompany);
        updatedCompany
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .addressDirection(UPDATED_ADDRESS_DIRECTION)
            .addressNumber(UPDATED_ADDRESS_NUMBER)
            .floor(UPDATED_FLOOR)
            .departament(UPDATED_DEPARTAMENT)
            .cuit(UPDATED_CUIT)
            .isSubscripted(UPDATED_IS_SUBSCRIPTED)
            .fantasyName(UPDATED_FANTASY_NAME)
            .postalCode(UPDATED_POSTAL_CODE)
            .tlf(UPDATED_TLF)
            .internalTlf(UPDATED_INTERNAL_TLF)
            .contact(UPDATED_CONTACT)
            .cellphone(UPDATED_CELLPHONE)
            .visitsQtyMin(UPDATED_VISITS_QTY_MIN)
            .visitsQtyMax(UPDATED_VISITS_QTY_MAX)
            .habPrim(UPDATED_HAB_PRIM)
            .habSec(UPDATED_HAB_SEC)
            .comment(UPDATED_COMMENT)
            .legislationId(UPDATED_LEGISLATION_ID)
            .solicitadorId(UPDATED_SOLICITADOR_ID)
            .ambitoId(UPDATED_AMBITO_ID)
            .autoridadId(UPDATED_AUTORIDAD_ID)
            .frecuencyTypeId(UPDATED_FRECUENCY_TYPE_ID)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restCompanyMockMvc.perform(put("/api/companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompany)))
            .andExpect(status().isOk());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
        Company testCompany = companyList.get(companyList.size() - 1);
        assertThat(testCompany.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCompany.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCompany.getAddressDirection()).isEqualTo(UPDATED_ADDRESS_DIRECTION);
        assertThat(testCompany.getAddressNumber()).isEqualTo(UPDATED_ADDRESS_NUMBER);
        assertThat(testCompany.getFloor()).isEqualTo(UPDATED_FLOOR);
        assertThat(testCompany.getDepartament()).isEqualTo(UPDATED_DEPARTAMENT);
        assertThat(testCompany.getCuit()).isEqualTo(UPDATED_CUIT);
        assertThat(testCompany.isIsSubscripted()).isEqualTo(UPDATED_IS_SUBSCRIPTED);
        assertThat(testCompany.getFantasyName()).isEqualTo(UPDATED_FANTASY_NAME);
        assertThat(testCompany.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testCompany.getTlf()).isEqualTo(UPDATED_TLF);
        assertThat(testCompany.getInternalTlf()).isEqualTo(UPDATED_INTERNAL_TLF);
        assertThat(testCompany.getContact()).isEqualTo(UPDATED_CONTACT);
        assertThat(testCompany.getCellphone()).isEqualTo(UPDATED_CELLPHONE);
        assertThat(testCompany.getVisitsQtyMin()).isEqualTo(UPDATED_VISITS_QTY_MIN);
        assertThat(testCompany.getVisitsQtyMax()).isEqualTo(UPDATED_VISITS_QTY_MAX);
        assertThat(testCompany.getHabPrim()).isEqualTo(UPDATED_HAB_PRIM);
        assertThat(testCompany.getHabSec()).isEqualTo(UPDATED_HAB_SEC);
        assertThat(testCompany.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testCompany.getLegislationId()).isEqualTo(UPDATED_LEGISLATION_ID);
        assertThat(testCompany.getSolicitadorId()).isEqualTo(UPDATED_SOLICITADOR_ID);
        assertThat(testCompany.getAmbitoId()).isEqualTo(UPDATED_AMBITO_ID);
        assertThat(testCompany.getAutoridadId()).isEqualTo(UPDATED_AUTORIDAD_ID);
        assertThat(testCompany.getFrecuencyTypeId()).isEqualTo(UPDATED_FRECUENCY_TYPE_ID);
        assertThat(testCompany.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testCompany.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);

        // Validate the Company in Elasticsearch
        verify(mockCompanySearchRepository, times(1)).save(testCompany);
    }

    @Test
    @Transactional
    public void updateNonExistingCompany() throws Exception {
        int databaseSizeBeforeUpdate = companyRepository.findAll().size();

        // Create the Company

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyMockMvc.perform(put("/api/companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isBadRequest());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Company in Elasticsearch
        verify(mockCompanySearchRepository, times(0)).save(company);
    }

    @Test
    @Transactional
    public void deleteCompany() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        int databaseSizeBeforeDelete = companyRepository.findAll().size();

        // Delete the company
        restCompanyMockMvc.perform(delete("/api/companies/{id}", company.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Company in Elasticsearch
        verify(mockCompanySearchRepository, times(1)).deleteById(company.getId());
    }

    @Test
    @Transactional
    public void searchCompany() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);
        when(mockCompanySearchRepository.search(queryStringQuery("id:" + company.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(company), PageRequest.of(0, 1), 1));
        // Search the company
        restCompanyMockMvc.perform(get("/api/_search/companies?query=id:" + company.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(company.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].addressDirection").value(hasItem(DEFAULT_ADDRESS_DIRECTION)))
            .andExpect(jsonPath("$.[*].addressNumber").value(hasItem(DEFAULT_ADDRESS_NUMBER)))
            .andExpect(jsonPath("$.[*].floor").value(hasItem(DEFAULT_FLOOR)))
            .andExpect(jsonPath("$.[*].departament").value(hasItem(DEFAULT_DEPARTAMENT)))
            .andExpect(jsonPath("$.[*].cuit").value(hasItem(DEFAULT_CUIT)))
            .andExpect(jsonPath("$.[*].isSubscripted").value(hasItem(DEFAULT_IS_SUBSCRIPTED.booleanValue())))
            .andExpect(jsonPath("$.[*].fantasyName").value(hasItem(DEFAULT_FANTASY_NAME)))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].tlf").value(hasItem(DEFAULT_TLF)))
            .andExpect(jsonPath("$.[*].internalTlf").value(hasItem(DEFAULT_INTERNAL_TLF)))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT)))
            .andExpect(jsonPath("$.[*].cellphone").value(hasItem(DEFAULT_CELLPHONE)))
            .andExpect(jsonPath("$.[*].visitsQtyMin").value(hasItem(DEFAULT_VISITS_QTY_MIN)))
            .andExpect(jsonPath("$.[*].visitsQtyMax").value(hasItem(DEFAULT_VISITS_QTY_MAX)))
            .andExpect(jsonPath("$.[*].habPrim").value(hasItem(DEFAULT_HAB_PRIM)))
            .andExpect(jsonPath("$.[*].habSec").value(hasItem(DEFAULT_HAB_SEC)))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].legislationId").value(hasItem(DEFAULT_LEGISLATION_ID)))
            .andExpect(jsonPath("$.[*].solicitadorId").value(hasItem(DEFAULT_SOLICITADOR_ID)))
            .andExpect(jsonPath("$.[*].ambitoId").value(hasItem(DEFAULT_AMBITO_ID)))
            .andExpect(jsonPath("$.[*].autoridadId").value(hasItem(DEFAULT_AUTORIDAD_ID)))
            .andExpect(jsonPath("$.[*].frecuencyTypeId").value(hasItem(DEFAULT_FRECUENCY_TYPE_ID)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }
}
