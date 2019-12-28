package com.mycompany.myapp.repository.search;
import com.mycompany.myapp.domain.Expiration;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Expiration} entity.
 */
public interface ExpirationSearchRepository extends ElasticsearchRepository<Expiration, Long> {
}
