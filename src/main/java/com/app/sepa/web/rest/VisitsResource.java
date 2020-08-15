package com.app.sepa.web.rest;

import com.app.sepa.domain.Visits;
import com.app.sepa.repository.VisitsRepository;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.app.sepa.domain.Visits}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class VisitsResource {

    private final Logger log = LoggerFactory.getLogger(VisitsResource.class);

    private static final String ENTITY_NAME = "visits";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VisitsRepository visitsRepository;

    public VisitsResource(VisitsRepository visitsRepository) {
        this.visitsRepository = visitsRepository;
    }

    /**
     * {@code POST  /visits} : Create a new visits.
     *
     * @param visits the visits to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new visits, or with status {@code 400 (Bad Request)} if the visits has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/visits")
    public ResponseEntity<Visits> createVisits(@Valid @RequestBody Visits visits) throws URISyntaxException {
        log.debug("REST request to save Visits : {}", visits);
        if (visits.getId() != null) {
            throw new BadRequestAlertException("A new visits cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GenerateEmployee(visits);
        Visits result = visitsRepository.save(visits);
        return ResponseEntity.created(new URI("/api/visits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /visits} : Updates an existing visits.
     *
     * @param visits the visits to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated visits,
     * or with status {@code 400 (Bad Request)} if the visits is not valid,
     * or with status {@code 500 (Internal Server Error)} if the visits couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/visits")
    public ResponseEntity<Visits> updateVisits(@Valid @RequestBody Visits visits) throws URISyntaxException {
        log.debug("REST request to update Visits : {}", visits);
        if (visits.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GenerateEmployee(visits);
        Visits result = visitsRepository.save(visits);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, visits.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /visits} : get all the visits.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of visits in body.
     */
    @GetMapping("/visits")
    public ResponseEntity<List<Visits>> getAllVisits(Pageable pageable) {
        log.debug("REST request to get a page of Visits");
        Page<Visits> page = visitsRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /visits/:id} : get the "id" visits.
     *
     * @param id the id of the visits to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the visits, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/visits/{id}")
    public ResponseEntity<Visits> getVisits(@PathVariable Long id) {
        log.debug("REST request to get Visits : {}", id);
        Optional<Visits> visits = visitsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(visits);
    }

    /**
     * {@code DELETE  /visits/:id} : delete the "id" visits.
     *
     * @param id the id of the visits to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/visits/{id}")
    public ResponseEntity<Void> deleteVisits(@PathVariable Long id) {
        log.debug("REST request to delete Visits : {}", id);
        visitsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * This function sets the employee for the visit acording to the company selected.
     * 
     * @param actualVisit the visit we`re gonna modify.
     */
    public void GenerateEmployee(Visits actualVisit)
    {
        if (actualVisit.getCompany() == null)
            actualVisit.setEmployee(null);
        else
        {
            if (actualVisit.getCompany().getEmployee() == null)
                actualVisit.setEmployee(null);
            else
                actualVisit.setEmployee(actualVisit.getCompany().getEmployee().getName() + " " + actualVisit.getCompany().getEmployee().getSurname());
        }
    }
}
