package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Localidadandpartido;
import com.mycompany.myapp.repository.LocalidadandpartidoRepository;
import com.mycompany.myapp.repository.search.LocalidadandpartidoSearchRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Localidadandpartido}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class LocalidadandpartidoResource {

    private final Logger log = LoggerFactory.getLogger(LocalidadandpartidoResource.class);

    private static final String ENTITY_NAME = "localidadandpartido";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LocalidadandpartidoRepository localidadandpartidoRepository;

    private final LocalidadandpartidoSearchRepository localidadandpartidoSearchRepository;

    public LocalidadandpartidoResource(LocalidadandpartidoRepository localidadandpartidoRepository, LocalidadandpartidoSearchRepository localidadandpartidoSearchRepository) {
        this.localidadandpartidoRepository = localidadandpartidoRepository;
        this.localidadandpartidoSearchRepository = localidadandpartidoSearchRepository;
    }

    /**
     * {@code POST  /localidadandpartidos} : Create a new localidadandpartido.
     *
     * @param localidadandpartido the localidadandpartido to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new localidadandpartido, or with status {@code 400 (Bad Request)} if the localidadandpartido has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/localidadandpartidos")
    public ResponseEntity<Localidadandpartido> createLocalidadandpartido(@Valid @RequestBody Localidadandpartido localidadandpartido) throws URISyntaxException {
        log.debug("REST request to save Localidadandpartido : {}", localidadandpartido);
        if (localidadandpartido.getId() != null) {
            throw new BadRequestAlertException("A new localidadandpartido cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Localidadandpartido result = localidadandpartidoRepository.save(localidadandpartido);
        localidadandpartidoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/localidadandpartidos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /localidadandpartidos} : Updates an existing localidadandpartido.
     *
     * @param localidadandpartido the localidadandpartido to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated localidadandpartido,
     * or with status {@code 400 (Bad Request)} if the localidadandpartido is not valid,
     * or with status {@code 500 (Internal Server Error)} if the localidadandpartido couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/localidadandpartidos")
    public ResponseEntity<Localidadandpartido> updateLocalidadandpartido(@Valid @RequestBody Localidadandpartido localidadandpartido) throws URISyntaxException {
        log.debug("REST request to update Localidadandpartido : {}", localidadandpartido);
        if (localidadandpartido.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Localidadandpartido result = localidadandpartidoRepository.save(localidadandpartido);
        localidadandpartidoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, localidadandpartido.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /localidadandpartidos} : get all the localidadandpartidos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of localidadandpartidos in body.
     */
    @GetMapping("/localidadandpartidos")
    public ResponseEntity<List<Localidadandpartido>> getAllLocalidadandpartidos(Pageable pageable) {
        log.debug("REST request to get a page of Localidadandpartidos");
        Page<Localidadandpartido> page = localidadandpartidoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /localidadandpartidos/:id} : get the "id" localidadandpartido.
     *
     * @param id the id of the localidadandpartido to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the localidadandpartido, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/localidadandpartidos/{id}")
    public ResponseEntity<Localidadandpartido> getLocalidadandpartido(@PathVariable Long id) {
        log.debug("REST request to get Localidadandpartido : {}", id);
        Optional<Localidadandpartido> localidadandpartido = localidadandpartidoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(localidadandpartido);
    }

    /**
     * {@code DELETE  /localidadandpartidos/:id} : delete the "id" localidadandpartido.
     *
     * @param id the id of the localidadandpartido to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/localidadandpartidos/{id}")
    public ResponseEntity<Void> deleteLocalidadandpartido(@PathVariable Long id) {
        log.debug("REST request to delete Localidadandpartido : {}", id);
        localidadandpartidoRepository.deleteById(id);
        localidadandpartidoSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/localidadandpartidos?query=:query} : search for the localidadandpartido corresponding
     * to the query.
     *
     * @param query the query of the localidadandpartido search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/localidadandpartidos")
    public ResponseEntity<List<Localidadandpartido>> searchLocalidadandpartidos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Localidadandpartidos for query {}", query);
        Page<Localidadandpartido> page = localidadandpartidoSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
