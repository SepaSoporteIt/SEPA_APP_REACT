package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.IndustryType;
import com.mycompany.myapp.repository.IndustryTypeRepository;
import com.mycompany.myapp.repository.search.IndustryTypeSearchRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.IndustryType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class IndustryTypeResource {

    private final Logger log = LoggerFactory.getLogger(IndustryTypeResource.class);

    private static final String ENTITY_NAME = "industryType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IndustryTypeRepository industryTypeRepository;

    private final IndustryTypeSearchRepository industryTypeSearchRepository;

    public IndustryTypeResource(IndustryTypeRepository industryTypeRepository, IndustryTypeSearchRepository industryTypeSearchRepository) {
        this.industryTypeRepository = industryTypeRepository;
        this.industryTypeSearchRepository = industryTypeSearchRepository;
    }

    /**
     * {@code POST  /industry-types} : Create a new industryType.
     *
     * @param industryType the industryType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new industryType, or with status {@code 400 (Bad Request)} if the industryType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/industry-types")
    public ResponseEntity<IndustryType> createIndustryType(@Valid @RequestBody IndustryType industryType) throws URISyntaxException {
        log.debug("REST request to save IndustryType : {}", industryType);
        if (industryType.getId() != null) {
            throw new BadRequestAlertException("A new industryType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IndustryType result = industryTypeRepository.save(industryType);
        industryTypeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/industry-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /industry-types} : Updates an existing industryType.
     *
     * @param industryType the industryType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated industryType,
     * or with status {@code 400 (Bad Request)} if the industryType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the industryType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/industry-types")
    public ResponseEntity<IndustryType> updateIndustryType(@Valid @RequestBody IndustryType industryType) throws URISyntaxException {
        log.debug("REST request to update IndustryType : {}", industryType);
        if (industryType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IndustryType result = industryTypeRepository.save(industryType);
        industryTypeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, industryType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /industry-types} : get all the industryTypes.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of industryTypes in body.
     */
    @GetMapping("/industry-types")
    public ResponseEntity<List<IndustryType>> getAllIndustryTypes(Pageable pageable) {
        log.debug("REST request to get a page of IndustryTypes");
        Page<IndustryType> page = industryTypeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /industry-types/:id} : get the "id" industryType.
     *
     * @param id the id of the industryType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the industryType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/industry-types/{id}")
    public ResponseEntity<IndustryType> getIndustryType(@PathVariable Long id) {
        log.debug("REST request to get IndustryType : {}", id);
        Optional<IndustryType> industryType = industryTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(industryType);
    }

    /**
     * {@code DELETE  /industry-types/:id} : delete the "id" industryType.
     *
     * @param id the id of the industryType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/industry-types/{id}")
    public ResponseEntity<Void> deleteIndustryType(@PathVariable Long id) {
        log.debug("REST request to delete IndustryType : {}", id);
        industryTypeRepository.deleteById(id);
        industryTypeSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/industry-types?query=:query} : search for the industryType corresponding
     * to the query.
     *
     * @param query the query of the industryType search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/industry-types")
    public ResponseEntity<List<IndustryType>> searchIndustryTypes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of IndustryTypes for query {}", query);
        Page<IndustryType> page = industryTypeSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
