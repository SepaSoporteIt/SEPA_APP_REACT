package com.app.sepa.repository;

import com.app.sepa.domain.LocalidadAndPartido;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the LocalidadAndPartido entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocalidadAndPartidoRepository extends JpaRepository<LocalidadAndPartido, Long> {
}
