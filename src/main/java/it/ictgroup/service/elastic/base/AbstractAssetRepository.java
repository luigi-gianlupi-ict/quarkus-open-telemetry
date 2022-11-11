package it.ictgroup.service.elastic;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.RequestBase;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.ResponseBody;
import it.ictgroup.model.pojo.PaginatedResponse;
import it.ictgroup.utils.elastic.ElasticUtils;
import it.ictgroup.utils.elastic.SearchUtils;
import org.jboss.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.core.MultivaluedMap;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import static it.ictgroup.service.AppConstants.*;
import static it.ictgroup.utils.elastic.ElasticUtils.WITH;


@SuppressWarnings("unused")
public abstract class AbstractAssetRepository implements Serializable {

    final Logger LOG = Logger.getLogger(getClass());


    @Inject
    ElasticsearchClient client;

    protected void logRequest(RequestBase searchRequest, String indexValue) {
        LOG.infof(String.format("-----------------> ElasticSearch Query %s ----------------- \n %s -------------------------------------",
                indexValue,
                searchRequest));
    }

    protected void logRequest(RequestBase searchRequest, List<String> indexValues) {
        LOG.infof(String.format("-----------------> ElasticSearch Query %s ----------------- \n %s -------------------------------------",
                String.join(",", indexValues),
                searchRequest));
    }

    protected void logResponse(ResponseBody<?> response) {
        logResponse(response, false);
    }

    protected void logResponse(ResponseBody<?> response, boolean showResponse) {
        LOG.infof("<----------------- ElasticSearch Response TOOK %d MS -------------------------------------", response.took());
        if (showResponse) {
            LOG.infof("<----------------- ElasticSearch Response %s  \n  -------------------------------------", response);
        } else {
            LOG.debugf("<----------------- ElasticSearch Response %s  \n  -------------------------------------", response);
        }
    }

    public PaginatedResponse<Map<String, Object>> search(MultivaluedMap<String, String> parameters,
                                                         String index, String type,
                                                         String commission, Integer startRow,
                                                         Integer pageSize, String orderBy) {

        try {
            final BoolQuery.Builder boolQueryBuilder = SearchUtils.query(MODEL,
                            Arrays.asList(UUID, USER, DATAIN, DATAOUT, VERSION), parameters)
                    .must(SearchUtils.buildCommissionQuery(commission))
                    .must(m -> m.exists(e -> e.field(DATAOUT)));

            SearchRequest.Builder searchRequestBuilder = new SearchRequest.Builder()
                    .index(index + WITH + type)
                    .size(pageSize)
                    .from(startRow)
                    .query(boolQueryBuilder.build()._toQuery());

            SearchUtils.orderBy("", orderBy).forEach(searchRequestBuilder::sort);
            SearchRequest searchRequest = searchRequestBuilder.build();

            logRequest(searchRequest, searchRequest.index());

            SearchResponse<?> response = client.search(searchRequest, Map.class);
            LOG.debug("Response: " + response);

            return Optional.of(response)
                    .map(SearchResponse::hits)
                    .filter(h -> h.total() != null)
                    .filter(h -> h.total().value() > 0)
                    .map(h -> new PaginatedResponse<>(h.total().value(), h.hits().stream()
                            .map(ElasticUtils::buildDocument)
                            .collect(Collectors.toList())))
                    .orElseGet(PaginatedResponse::new);

        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }


    }
}