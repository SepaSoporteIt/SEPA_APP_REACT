package com.app.sepa.repository;

import com.app.sepa.domain.Visits;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Visits entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VisitsRepository extends JpaRepository<Visits, Long> {
}
