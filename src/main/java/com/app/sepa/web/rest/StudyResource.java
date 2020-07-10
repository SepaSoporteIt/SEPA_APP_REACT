package com.app.sepa.web.rest;

import com.app.sepa.domain.Study;
import com.app.sepa.repository.StudyRepository;
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
 * REST controller for managing {@link com.app.sepa.domain.Study}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class StudyResource {

    private final Logger log = LoggerFactory.getLogger(StudyResource.class);

    private static final String ENTITY_NAME = "study";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StudyRepository studyRepository;

    public StudyResource(StudyRepository studyRepository) {
        this.studyRepository = studyRepository;
    }

    /**
     * {@code POST  /studies} : Create a new study.
     *
     * @param study the study to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new study, or with status {@code 400 (Bad Request)} if the study has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/studies")
    public ResponseEntity<Study> createStudy(@Valid @RequestBody Study study) throws URISyntaxException {
        log.debug("REST request to save Study : {}", study);
        if (study.getId() != null) {
            throw new BadRequestAlertException("A new study cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Study result = studyRepository.save(study);
        return ResponseEntity.created(new URI("/api/studies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /studies} : Updates an existing study.
     *
     * @param study the study to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated study,
     * or with status {@code 400 (Bad Request)} if the study is not valid,
     * or with status {@code 500 (Internal Server Error)} if the study couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/studies")
    public ResponseEntity<Study> updateStudy(@Valid @RequestBody Study study) throws URISyntaxException {
        log.debug("REST request to update Study : {}", study);
        if (study.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Study result = studyRepository.save(study);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, study.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /studies} : get all the studies.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of studies in body.
     */
    @GetMapping("/studies")
    public ResponseEntity<List<Study>> getAllStudies(Pageable pageable) {
        log.debug("REST request to get a page of Studies");
        Page<Study> page = studyRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /studies/:id} : get the "id" study.
     *
     * @param id the id of the study to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the study, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/studies/{id}")
    public ResponseEntity<Study> getStudy(@PathVariable Long id) {
        log.debug("REST request to get Study : {}", id);
        Optional<Study> study = studyRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(study);
    }

    /**
     * {@code DELETE  /studies/:id} : delete the "id" study.
     *
     * @param id the id of the study to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/studies/{id}")
    public ResponseEntity<Void> deleteStudy(@PathVariable Long id) {
        log.debug("REST request to delete Study : {}", id);
        studyRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
