package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Localidadandpartido;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Localidadandpartido entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocalidadandpartidoRepository extends JpaRepository<Localidadandpartido, Long> {

}
