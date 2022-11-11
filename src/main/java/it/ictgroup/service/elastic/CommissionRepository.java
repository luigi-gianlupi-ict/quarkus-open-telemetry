package it.ictgroup.service.elastic;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import it.ictgroup.model.pojo.PaginatedResponse;
import it.ictgroup.service.elastic.base.AbstractAssetRepository;
import it.ictgroup.utils.elastic.ElasticUtils;
import it.ictgroup.utils.elastic.SearchUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;
import javax.inject.Singleton;
import javax.ws.rs.core.MultivaluedMap;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static it.ictgroup.service.AppConstants.*;
import static it.ictgroup.utils.elastic.ElasticUtils.WITH;


@Singleton
public class CommissionRepository extends AbstractAssetRepository implements Serializable {

    static final Logger LOG = Logger.getLogger(CommissionRepository.class);

    @ConfigProperty(name = "asset.default.index")
    String index_of_anagrafica;


    public PaginatedResponse<Map<String, Object>> getCommesse(MultivaluedMap<String, String> parameters,
                                                              List<String> codiciCommessa, Integer startRow,
                                                              Integer pageSize, String orderBy) {
        try {
            final BoolQuery.Builder queryBuilder = SearchUtils.query("", parameters);
            Optional.ofNullable(codiciCommessa)
                    .filter(strings -> !strings.isEmpty())
                    .map(list -> QueryBuilders.terms(t -> t.field(CODE + RAW_POSTFIX).terms(n -> n.value(list.stream().map(FieldValue::of).collect(Collectors.toList())))))
                    .ifPresent(queryBuilder::must);
            LOG.debug(codiciCommessa != null ? "lista non nulla" : "");
            LOG.debug(codiciCommessa != null && codiciCommessa.size() > 0 ? "lista size > 0" : " size 0");
            SearchRequest.Builder searchRequestBuilder = new SearchRequest.Builder()
                    .index(index_of_anagrafica + WITH + COMMISSION)
                    .query(queryBuilder.build()._toQuery())
                    .from(startRow)
                    .size(pageSize);
            SearchUtils.orderBy("", orderBy).forEach(searchRequestBuilder::sort);
            SearchRequest searchRequest = searchRequestBuilder.build();
            logRequest(searchRequest, searchRequest.index());
            final SearchResponse<?> response = elasticsearchClient.search(searchRequest, Map.class);
            logResponse(response);
            if (response.hits() == null || response.hits().total() == null || response.hits().total().value() == 0) {
                return new PaginatedResponse<>();
            }
            final List<Map<String, Object>> list = response.hits().hits().stream()
                    .map(ElasticUtils::buildDocument)
                    .collect(Collectors.toList());
            return new PaginatedResponse<>(response.hits().total().value(), list);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}