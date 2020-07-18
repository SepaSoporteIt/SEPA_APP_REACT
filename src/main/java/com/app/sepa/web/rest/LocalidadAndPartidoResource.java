package com.app.sepa.web.rest;

import com.app.sepa.domain.LocalidadAndPartido;
import com.app.sepa.repository.LocalidadAndPartidoRepository;
import com.app.sepa.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.app.sepa.domain.LocalidadAndPartido}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class LocalidadAndPartidoResource {

    private final Logger log = LoggerFactory.getLogger(LocalidadAndPartidoResource.class);

    private static final String ENTITY_NAME = "localidadAndPartido";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LocalidadAndPartidoRepository localidadAndPartidoRepository;

    public LocalidadAndPartidoResource(LocalidadAndPartidoRepository localidadAndPartidoRepository) {
        this.localidadAndPartidoRepository = localidadAndPartidoRepository;
    }

    /**
     * {@code POST  /localidad-and-partidos} : Create a new localidadAndPartido.
     *
     * @param localidadAndPartido the localidadAndPartido to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new localidadAndPartido, or with status {@code 400 (Bad Request)} if the localidadAndPartido has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/localidad-and-partidos")
    public ResponseEntity<LocalidadAndPartido> createLocalidadAndPartido(@RequestBody LocalidadAndPartido localidadAndPartido) throws URISyntaxException {
        log.debug("REST request to save LocalidadAndPartido : {}", localidadAndPartido);
        if (localidadAndPartido.getId() != null) {
            throw new BadRequestAlertException("A new localidadAndPartido cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LocalidadAndPartido result = localidadAndPartidoRepository.save(localidadAndPartido);
        return ResponseEntity.created(new URI("/api/localidad-and-partidos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /localidad-and-partidos} : Updates an existing localidadAndPartido.
     *
     * @param localidadAndPartido the localidadAndPartido to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated localidadAndPartido,
     * or with status {@code 400 (Bad Request)} if the localidadAndPartido is not valid,
     * or with status {@code 500 (Internal Server Error)} if the localidadAndPartido couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/localidad-and-partidos")
    public ResponseEntity<LocalidadAndPartido> updateLocalidadAndPartido(@RequestBody LocalidadAndPartido localidadAndPartido) throws URISyntaxException {
        log.debug("REST request to update LocalidadAndPartido : {}", localidadAndPartido);
        if (localidadAndPartido.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LocalidadAndPartido result = localidadAndPartidoRepository.save(localidadAndPartido);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, localidadAndPartido.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /localidad-and-partidos} : get all the localidadAndPartidos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of localidadAndPartidos in body.
     */
    @GetMapping("/localidad-and-partidos")
    public ResponseEntity<List<LocalidadAndPartido>> getAllLocalidadAndPartidos(Pageable pageable) {
        log.debug("REST request to get a page of LocalidadAndPartidos");
        Page<LocalidadAndPartido> page = localidadAndPartidoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /localidad-and-partidos/:id} : get the "id" localidadAndPartido.
     *
     * @param id the id of the localidadAndPartido to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the localidadAndPartido, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/localidad-and-partidos/{id}")
    public ResponseEntity<LocalidadAndPartido> getLocalidadAndPartido(@PathVariable Long id) {
        log.debug("REST request to get LocalidadAndPartido : {}", id);
        Optional<LocalidadAndPartido> localidadAndPartido = localidadAndPartidoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(localidadAndPartido);
    }

    /**
     * {@code DELETE  /localidad-and-partidos/:id} : delete the "id" localidadAndPartido.
     *
     * @param id the id of the localidadAndPartido to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/localidad-and-partidos/{id}")
    public ResponseEntity<Void> deleteLocalidadAndPartido(@PathVariable Long id) {
        log.debug("REST request to delete LocalidadAndPartido : {}", id);
        localidadAndPartidoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
