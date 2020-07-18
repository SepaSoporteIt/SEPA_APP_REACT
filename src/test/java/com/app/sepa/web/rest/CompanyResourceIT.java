package com.app.sepa.web.rest;

import com.app.sepa.SepaApp;
import com.app.sepa.domain.Company;
import com.app.sepa.repository.CompanyRepository;

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
 * Integration tests for the {@link CompanyResource} REST controller.
 */
@SpringBootTest(classes = SepaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CompanyResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_DIRECTION = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_DIRECTION = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_BETWEEN_STREETS = "AAAAAAAAAA";
    private static final String UPDATED_BETWEEN_STREETS = "BBBBBBBBBB";

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

    private static final Boolean DEFAULT_IS_DISABLED = false;
    private static final Boolean UPDATED_IS_DISABLED = true;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompanyMockMvc;

    private Company company;

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
            .betweenStreets(DEFAULT_BETWEEN_STREETS)
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
            .isDisabled(DEFAULT_IS_DISABLED);
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
            .betweenStreets(UPDATED_BETWEEN_STREETS)
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
            .isDisabled(UPDATED_IS_DISABLED);
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
            .contentType(MediaType.APPLICATION_JSON)
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
        assertThat(testCompany.getBetweenStreets()).isEqualTo(DEFAULT_BETWEEN_STREETS);
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
        assertThat(testCompany.isIsDisabled()).isEqualTo(DEFAULT_IS_DISABLED);
    }

    @Test
    @Transactional
    public void createCompanyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyRepository.findAll().size();

        // Create the Company with an existing ID
        company.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyMockMvc.perform(post("/api/companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isBadRequest());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyRepository.findAll().size();
        // set the field null
        company.setName(null);

        // Create the Company, which fails.


        restCompanyMockMvc.perform(post("/api/companies")
            .contentType(MediaType.APPLICATION_JSON)
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
            .contentType(MediaType.APPLICATION_JSON)
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
            .contentType(MediaType.APPLICATION_JSON)
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
            .contentType(MediaType.APPLICATION_JSON)
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
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(company.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].addressDirection").value(hasItem(DEFAULT_ADDRESS_DIRECTION)))
            .andExpect(jsonPath("$.[*].addressNumber").value(hasItem(DEFAULT_ADDRESS_NUMBER)))
            .andExpect(jsonPath("$.[*].betweenStreets").value(hasItem(DEFAULT_BETWEEN_STREETS)))
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
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].isDisabled").value(hasItem(DEFAULT_IS_DISABLED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCompany() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get the company
        restCompanyMockMvc.perform(get("/api/companies/{id}", company.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(company.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.addressDirection").value(DEFAULT_ADDRESS_DIRECTION))
            .andExpect(jsonPath("$.addressNumber").value(DEFAULT_ADDRESS_NUMBER))
            .andExpect(jsonPath("$.betweenStreets").value(DEFAULT_BETWEEN_STREETS))
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
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT))
            .andExpect(jsonPath("$.isDisabled").value(DEFAULT_IS_DISABLED.booleanValue()));
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
            .betweenStreets(UPDATED_BETWEEN_STREETS)
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
            .isDisabled(UPDATED_IS_DISABLED);

        restCompanyMockMvc.perform(put("/api/companies")
            .contentType(MediaType.APPLICATION_JSON)
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
        assertThat(testCompany.getBetweenStreets()).isEqualTo(UPDATED_BETWEEN_STREETS);
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
        assertThat(testCompany.isIsDisabled()).isEqualTo(UPDATED_IS_DISABLED);
    }

    @Test
    @Transactional
    public void updateNonExistingCompany() throws Exception {
        int databaseSizeBeforeUpdate = companyRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyMockMvc.perform(put("/api/companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isBadRequest());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompany() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        int databaseSizeBeforeDelete = companyRepository.findAll().size();

        // Delete the company
        restCompanyMockMvc.perform(delete("/api/companies/{id}", company.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
