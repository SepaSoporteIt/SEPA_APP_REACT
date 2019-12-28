package com.mycompany.myapp.repository.search;
import com.mycompany.myapp.domain.IndustryType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link IndustryType} entity.
 */
public interface IndustryTypeSearchRepository extends ElasticsearchRepository<IndustryType, Long> {
}
