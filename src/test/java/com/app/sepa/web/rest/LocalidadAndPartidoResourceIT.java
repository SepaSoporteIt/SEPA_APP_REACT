package com.app.sepa.web.rest;

import com.app.sepa.SepaApp;
import com.app.sepa.domain.LocalidadAndPartido;
import com.app.sepa.repository.LocalidadAndPartidoRepository;

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
 * Integration tests for the {@link LocalidadAndPartidoResource} REST controller.
 */
@SpringBootTest(classes = SepaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class LocalidadAndPartidoResourceIT {

    private static final String DEFAULT_LOCALIDAD = "AAAAAAAAAA";
    private static final String UPDATED_LOCALIDAD = "BBBBBBBBBB";

    private static final String DEFAULT_PARTIDO = "AAAAAAAAAA";
    private static final String UPDATED_PARTIDO = "BBBBBBBBBB";

    @Autowired
    private LocalidadAndPartidoRepository localidadAndPartidoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLocalidadAndPartidoMockMvc;

    private LocalidadAndPartido localidadAndPartido;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LocalidadAndPartido createEntity(EntityManager em) {
        LocalidadAndPartido localidadAndPartido = new LocalidadAndPartido()
            .localidad(DEFAULT_LOCALIDAD)
            .partido(DEFAULT_PARTIDO);
        return localidadAndPartido;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LocalidadAndPartido createUpdatedEntity(EntityManager em) {
        LocalidadAndPartido localidadAndPartido = new LocalidadAndPartido()
            .localidad(UPDATED_LOCALIDAD)
            .partido(UPDATED_PARTIDO);
        return localidadAndPartido;
    }

    @BeforeEach
    public void initTest() {
        localidadAndPartido = createEntity(em);
    }

    @Test
    @Transactional
    public void createLocalidadAndPartido() throws Exception {
        int databaseSizeBeforeCreate = localidadAndPartidoRepository.findAll().size();
        // Create the LocalidadAndPartido
        restLocalidadAndPartidoMockMvc.perform(post("/api/localidad-and-partidos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(localidadAndPartido)))
            .andExpect(status().isCreated());

        // Validate the LocalidadAndPartido in the database
        List<LocalidadAndPartido> localidadAndPartidoList = localidadAndPartidoRepository.findAll();
        assertThat(localidadAndPartidoList).hasSize(databaseSizeBeforeCreate + 1);
        LocalidadAndPartido testLocalidadAndPartido = localidadAndPartidoList.get(localidadAndPartidoList.size() - 1);
        assertThat(testLocalidadAndPartido.getLocalidad()).isEqualTo(DEFAULT_LOCALIDAD);
        assertThat(testLocalidadAndPartido.getPartido()).isEqualTo(DEFAULT_PARTIDO);
    }

    @Test
    @Transactional
    public void createLocalidadAndPartidoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = localidadAndPartidoRepository.findAll().size();

        // Create the LocalidadAndPartido with an existing ID
        localidadAndPartido.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocalidadAndPartidoMockMvc.perform(post("/api/localidad-and-partidos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(localidadAndPartido)))
            .andExpect(status().isBadRequest());

        // Validate the LocalidadAndPartido in the database
        List<LocalidadAndPartido> localidadAndPartidoList = localidadAndPartidoRepository.findAll();
        assertThat(localidadAndPartidoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLocalidadAndPartidos() throws Exception {
        // Initialize the database
        localidadAndPartidoRepository.saveAndFlush(localidadAndPartido);

        // Get all the localidadAndPartidoList
        restLocalidadAndPartidoMockMvc.perform(get("/api/localidad-and-partidos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(localidadAndPartido.getId().intValue())))
            .andExpect(jsonPath("$.[*].localidad").value(hasItem(DEFAULT_LOCALIDAD)))
            .andExpect(jsonPath("$.[*].partido").value(hasItem(DEFAULT_PARTIDO)));
    }
    
    @Test
    @Transactional
    public void getLocalidadAndPartido() throws Exception {
        // Initialize the database
        localidadAndPartidoRepository.saveAndFlush(localidadAndPartido);

        // Get the localidadAndPartido
        restLocalidadAndPartidoMockMvc.perform(get("/api/localidad-and-partidos/{id}", localidadAndPartido.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(localidadAndPartido.getId().intValue()))
            .andExpect(jsonPath("$.localidad").value(DEFAULT_LOCALIDAD))
            .andExpect(jsonPath("$.partido").value(DEFAULT_PARTIDO));
    }
    @Test
    @Transactional
    public void getNonExistingLocalidadAndPartido() throws Exception {
        // Get the localidadAndPartido
        restLocalidadAndPartidoMockMvc.perform(get("/api/localidad-and-partidos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLocalidadAndPartido() throws Exception {
        // Initialize the database
        localidadAndPartidoRepository.saveAndFlush(localidadAndPartido);

        int databaseSizeBeforeUpdate = localidadAndPartidoRepository.findAll().size();

        // Update the localidadAndPartido
        LocalidadAndPartido updatedLocalidadAndPartido = localidadAndPartidoRepository.findById(localidadAndPartido.getId()).get();
        // Disconnect from session so that the updates on updatedLocalidadAndPartido are not directly saved in db
        em.detach(updatedLocalidadAndPartido);
        updatedLocalidadAndPartido
            .localidad(UPDATED_LOCALIDAD)
            .partido(UPDATED_PARTIDO);

        restLocalidadAndPartidoMockMvc.perform(put("/api/localidad-and-partidos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedLocalidadAndPartido)))
            .andExpect(status().isOk());

        // Validate the LocalidadAndPartido in the database
        List<LocalidadAndPartido> localidadAndPartidoList = localidadAndPartidoRepository.findAll();
        assertThat(localidadAndPartidoList).hasSize(databaseSizeBeforeUpdate);
        LocalidadAndPartido testLocalidadAndPartido = localidadAndPartidoList.get(localidadAndPartidoList.size() - 1);
        assertThat(testLocalidadAndPartido.getLocalidad()).isEqualTo(UPDATED_LOCALIDAD);
        assertThat(testLocalidadAndPartido.getPartido()).isEqualTo(UPDATED_PARTIDO);
    }

    @Test
    @Transactional
    public void updateNonExistingLocalidadAndPartido() throws Exception {
        int databaseSizeBeforeUpdate = localidadAndPartidoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocalidadAndPartidoMockMvc.perform(put("/api/localidad-and-partidos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(localidadAndPartido)))
            .andExpect(status().isBadRequest());

        // Validate the LocalidadAndPartido in the database
        List<LocalidadAndPartido> localidadAndPartidoList = localidadAndPartidoRepository.findAll();
        assertThat(localidadAndPartidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLocalidadAndPartido() throws Exception {
        // Initialize the database
        localidadAndPartidoRepository.saveAndFlush(localidadAndPartido);

        int databaseSizeBeforeDelete = localidadAndPartidoRepository.findAll().size();

        // Delete the localidadAndPartido
        restLocalidadAndPartidoMockMvc.perform(delete("/api/localidad-and-partidos/{id}", localidadAndPartido.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LocalidadAndPartido> localidadAndPartidoList = localidadAndPartidoRepository.findAll();
        assertThat(localidadAndPartidoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
