package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.Localidadandpartido;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Localidadandpartido} entity.
 */
public interface LocalidadandpartidoSearchRepository extends ElasticsearchRepository<Localidadandpartido, Long> {
}
