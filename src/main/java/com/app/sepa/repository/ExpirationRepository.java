package com.app.sepa.repository;

import com.app.sepa.domain.Expiration;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Expiration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExpirationRepository extends JpaRepository<Expiration, Long> {
}
