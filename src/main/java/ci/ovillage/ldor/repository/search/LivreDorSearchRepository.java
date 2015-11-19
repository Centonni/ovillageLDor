package ci.ovillage.ldor.repository.search;

import ci.ovillage.ldor.domain.LivreDor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the LivreDor entity.
 */
public interface LivreDorSearchRepository extends ElasticsearchRepository<LivreDor, Long> {
}
