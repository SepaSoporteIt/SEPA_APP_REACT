package com.app.sepa.repository;

import com.app.sepa.domain.IndustryType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the IndustryType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IndustryTypeRepository extends JpaRepository<IndustryType, Long> {
}
