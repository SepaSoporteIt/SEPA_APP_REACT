package com.mycompany.myapp.repository.search;
import com.mycompany.myapp.domain.Study;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Study} entity.
 */
public interface StudySearchRepository extends ElasticsearchRepository<Study, Long> {
}
