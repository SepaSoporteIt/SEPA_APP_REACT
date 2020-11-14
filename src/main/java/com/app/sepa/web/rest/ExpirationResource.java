package com.app.sepa.web.rest;

import com.app.sepa.domain.Expiration;
import com.app.sepa.domain.enumeration.Status;
import com.app.sepa.repository.ExpirationRepository;
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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.app.sepa.domain.Expiration}.
 */
@RestController
@RequestMapping("/api")
@Transactional

public class ExpirationResource {

    private final Logger log = LoggerFactory.getLogger(ExpirationResource.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    private static final String ENTITY_NAME = "expiration";

    private List<Expiration> expirationList;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExpirationRepository expirationRepository;

    public ExpirationResource(ExpirationRepository expirationRepository) {
        this.expirationRepository = expirationRepository;
    }

    /**
     * {@code POST  /expirations} : Create a new expiration.
     *
     * @param expiration the expiration to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new expiration, or with status {@code 400 (Bad Request)} if the expiration has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/expirations")
    public ResponseEntity<Expiration> createExpiration(@Valid @RequestBody Expiration expiration) throws URISyntaxException {
        log.debug("REST request to save Expiration : {}", expiration);
        if (expiration.getId() != null) {
            throw new BadRequestAlertException("A new expiration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        expiration.generateUniqueCode();
        expiration.checkDate();
        Expiration result = expirationRepository.save(expiration);
        return ResponseEntity.created(new URI("/api/expirations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /expirations} : Updates an existing expiration.
     *
     * @param expiration the expiration to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated expiration,
     * or with status {@code 400 (Bad Request)} if the expiration is not valid,
     * or with status {@code 500 (Internal Server Error)} if the expiration couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/expirations")
    public ResponseEntity<Expiration> updateExpiration(@Valid @RequestBody Expiration expiration) throws URISyntaxException {
        log.debug("REST request to update Expiration : {}", expiration);
        if (expiration.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        expiration.generateUniqueCode();
        expiration.checkDate();
        Expiration result = expirationRepository.save(expiration);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, expiration.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /expirations} : get all the expirations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of expirations in body.
     */
    @GetMapping("/expirations")
    public ResponseEntity<List<Expiration>> getAllExpirations(Pageable pageable) {
        log.debug("REST request to get a page of Expirations");
        Page<Expiration> page = expirationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /expirations/:id} : get the "id" expiration.
     *
     * @param id the id of the expiration to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the expiration, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/expirations/{id}")
    public ResponseEntity<Expiration> getExpiration(@PathVariable Long id) {
        log.debug("REST request to get Expiration : {}", id);
        Optional<Expiration> expiration = expirationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(expiration);
    }

    /**
     * {@code DELETE  /expirations/:id} : delete the "id" expiration.
     *
     * @param id the id of the expiration to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/expirations/{id}")
    public ResponseEntity<Void> deleteExpiration(@PathVariable Long id) {
        log.debug("REST request to delete Expiration : {}", id);
        expirationRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * Change the state of all expirations based on the actual date and the due date.
     */
    @Scheduled(fixedDelay = 18000000)
    public void CheckDatesLogicInAllExpirations()
    {
        log.info("Started check on expitations at {}", dateTimeFormatter.format(LocalDateTime.now()));
        expirationList = expirationRepository.findAll();

        if (!expirationList.isEmpty())
        {
            for (Expiration expiration : expirationList) 
            {
                expiration.checkDate();
            }
        }
        else
            log.info("There's no expiration on the DB");
        log.info("Finished check on expitations at {}", dateTimeFormatter.format(LocalDateTime.now()));
    }
}
