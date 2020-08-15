package com.app.sepa.web.rest;

import com.app.sepa.SepaApp;
import com.app.sepa.domain.Visits;
import com.app.sepa.domain.Company;
import com.app.sepa.repository.VisitsRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link VisitsResource} REST controller.
 */
@SpringBootTest(classes = SepaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class VisitsResourceIT {

    private static final String DEFAULT_EMPLOYEE = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_VISIT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VISIT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    @Autowired
    private VisitsRepository visitsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVisitsMockMvc;

    private Visits visits;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Visits createEntity(EntityManager em) {
        Visits visits = new Visits()
            .employee(DEFAULT_EMPLOYEE)
            .visit_date(DEFAULT_VISIT_DATE)
            .comments(DEFAULT_COMMENTS);
        // Add required entity
        Company company;
        if (TestUtil.findAll(em, Company.class).isEmpty()) {
            company = CompanyResourceIT.createEntity(em);
            em.persist(company);
            em.flush();
        } else {
            company = TestUtil.findAll(em, Company.class).get(0);
        }
        visits.setCompany(company);
        return visits;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Visits createUpdatedEntity(EntityManager em) {
        Visits visits = new Visits()
            .employee(UPDATED_EMPLOYEE)
            .visit_date(UPDATED_VISIT_DATE)
            .comments(UPDATED_COMMENTS);
        // Add required entity
        Company company;
        if (TestUtil.findAll(em, Company.class).isEmpty()) {
            company = CompanyResourceIT.createUpdatedEntity(em);
            em.persist(company);
            em.flush();
        } else {
            company = TestUtil.findAll(em, Company.class).get(0);
        }
        visits.setCompany(company);
        return visits;
    }

    @BeforeEach
    public void initTest() {
        visits = createEntity(em);
    }

    @Test
    @Transactional
    public void createVisits() throws Exception {
        int databaseSizeBeforeCreate = visitsRepository.findAll().size();
        // Create the Visits
        restVisitsMockMvc.perform(post("/api/visits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visits)))
            .andExpect(status().isCreated());

        // Validate the Visits in the database
        List<Visits> visitsList = visitsRepository.findAll();
        assertThat(visitsList).hasSize(databaseSizeBeforeCreate + 1);
        Visits testVisits = visitsList.get(visitsList.size() - 1);
        assertThat(testVisits.getEmployee()).isEqualTo(DEFAULT_EMPLOYEE);
        assertThat(testVisits.getVisit_date()).isEqualTo(DEFAULT_VISIT_DATE);
        assertThat(testVisits.getComments()).isEqualTo(DEFAULT_COMMENTS);
    }

    @Test
    @Transactional
    public void createVisitsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = visitsRepository.findAll().size();

        // Create the Visits with an existing ID
        visits.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVisitsMockMvc.perform(post("/api/visits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visits)))
            .andExpect(status().isBadRequest());

        // Validate the Visits in the database
        List<Visits> visitsList = visitsRepository.findAll();
        assertThat(visitsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllVisits() throws Exception {
        // Initialize the database
        visitsRepository.saveAndFlush(visits);

        // Get all the visitsList
        restVisitsMockMvc.perform(get("/api/visits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(visits.getId().intValue())))
            .andExpect(jsonPath("$.[*].employee").value(hasItem(DEFAULT_EMPLOYEE)))
            .andExpect(jsonPath("$.[*].visit_date").value(hasItem(DEFAULT_VISIT_DATE.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)));
    }
    
    @Test
    @Transactional
    public void getVisits() throws Exception {
        // Initialize the database
        visitsRepository.saveAndFlush(visits);

        // Get the visits
        restVisitsMockMvc.perform(get("/api/visits/{id}", visits.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(visits.getId().intValue()))
            .andExpect(jsonPath("$.employee").value(DEFAULT_EMPLOYEE))
            .andExpect(jsonPath("$.visit_date").value(DEFAULT_VISIT_DATE.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS));
    }
    @Test
    @Transactional
    public void getNonExistingVisits() throws Exception {
        // Get the visits
        restVisitsMockMvc.perform(get("/api/visits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVisits() throws Exception {
        // Initialize the database
        visitsRepository.saveAndFlush(visits);

        int databaseSizeBeforeUpdate = visitsRepository.findAll().size();

        // Update the visits
        Visits updatedVisits = visitsRepository.findById(visits.getId()).get();
        // Disconnect from session so that the updates on updatedVisits are not directly saved in db
        em.detach(updatedVisits);
        updatedVisits
            .employee(UPDATED_EMPLOYEE)
            .visit_date(UPDATED_VISIT_DATE)
            .comments(UPDATED_COMMENTS);

        restVisitsMockMvc.perform(put("/api/visits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedVisits)))
            .andExpect(status().isOk());

        // Validate the Visits in the database
        List<Visits> visitsList = visitsRepository.findAll();
        assertThat(visitsList).hasSize(databaseSizeBeforeUpdate);
        Visits testVisits = visitsList.get(visitsList.size() - 1);
        assertThat(testVisits.getEmployee()).isEqualTo(UPDATED_EMPLOYEE);
        assertThat(testVisits.getVisit_date()).isEqualTo(UPDATED_VISIT_DATE);
        assertThat(testVisits.getComments()).isEqualTo(UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void updateNonExistingVisits() throws Exception {
        int databaseSizeBeforeUpdate = visitsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVisitsMockMvc.perform(put("/api/visits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visits)))
            .andExpect(status().isBadRequest());

        // Validate the Visits in the database
        List<Visits> visitsList = visitsRepository.findAll();
        assertThat(visitsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVisits() throws Exception {
        // Initialize the database
        visitsRepository.saveAndFlush(visits);

        int databaseSizeBeforeDelete = visitsRepository.findAll().size();

        // Delete the visits
        restVisitsMockMvc.perform(delete("/api/visits/{id}", visits.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Visits> visitsList = visitsRepository.findAll();
        assertThat(visitsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
