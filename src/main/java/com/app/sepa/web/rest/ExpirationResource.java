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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
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
    public ResponseEntity<Expiration> createExpiration(@RequestBody Expiration expiration) throws URISyntaxException {
        log.debug("REST request to save Expiration : {}", expiration);
        if (expiration.getId() != null) {
            throw new BadRequestAlertException("A new expiration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Expiration result = expirationRepository.save(expiration);
        GenerateUniqueCode(result);
        GenerateResponsible(result);
        CheckDatesLogic();
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
    public ResponseEntity<Expiration> updateExpiration(@RequestBody Expiration expiration) throws URISyntaxException {
        log.debug("REST request to update Expiration : {}", expiration);
        if (expiration.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Expiration result = expirationRepository.save(expiration);
        GenerateUniqueCode(result);
        GenerateResponsible(result);
        CheckDatesLogic();
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
    public ResponseEntity<List<Expiration>> getAllExpirations(Pageable pageable) 
    {
        expirationList = expirationRepository.findAll();
        for (Expiration expiration : expirationList)
        {
            GenerateUniqueCode(expiration);
            GenerateResponsible(expiration);
        }
        CheckDatesLogic();
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
    public ResponseEntity<Expiration> getExpiration(@PathVariable Long id) 
    {
        CheckDatesLogic();
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
     * Generates the expiration's responsible according to the company employee.
     * @param actualExpiration the expiration to set the responsible.
     */
    public void GenerateResponsible(Expiration actualExpiration)
    {
        String responsible;

        if (actualExpiration.getCompany() == null)
        {
            actualExpiration.setResponsible(null);
        }
        else
        {
            if (actualExpiration.getCompany().getEmployee()==null)
            {
                actualExpiration.setResponsible(null);
            }
            else
            {
                responsible = actualExpiration.getCompany().getEmployee().getName() + " " + actualExpiration.getCompany().getEmployee().getSurname();
                actualExpiration.setResponsible(responsible);
            }
        }  
    }

    /**
     * Generates the unique code using the company, employee, study and start date of the expiration
     * @param actualExpiration The expiration to modify
     */
    public void GenerateUniqueCode(Expiration actualExpiration)
    {
        String companyId;
        String employeeId;
        String studyId;

        if (actualExpiration.getCompany() == null)
        {
            companyId = "0";
        }
        else
        {
            companyId = actualExpiration.getCompany().getId().toString();
        }

        if (actualExpiration.getEmployee() == null)
        {
            employeeId = "0";
        }
        else
        {
            employeeId = actualExpiration.getEmployee().getId().toString();
        }

        if (actualExpiration.getStudy() == null)
        {
            studyId = "0";
        }
        else
        {
            studyId = actualExpiration.getStudy().getId().toString();
        }
        
        String initialDate = actualExpiration.getStartDate().toString();
        actualExpiration.setUniqueCode(companyId+"-"+employeeId+"-"+studyId+"-"+initialDate);
    }

    /**
     * Change the state of all expirations based on the actual date and the due date.
     */
    public void CheckDatesLogic()
    {
        expirationList = expirationRepository.findAll();

        for (Expiration expiration : expirationList) {

            LocalDate actualEndDate = expiration.getEndDate();
            LocalDate actualWarningDate = actualEndDate.minusDays(30);

            if (actualEndDate.isBefore(LocalDate.now()))
            {
                expiration.setStatus(Status.VENCIDO);
            }
            else if (actualWarningDate.isBefore(LocalDate.now()))
            {
                expiration.setStatus(Status.A_VENCER);
            }
        }
    }
}
