package it.ictgroup.resource;


import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import it.ictgroup.config.Config;
import it.ictgroup.model.pojo.PaginatedResponse;
import it.ictgroup.service.elastic.CommissionRepository;
import org.jboss.logging.Logger;

import java.util.Map;

@Path("/traced")
public class TracedResource {
    final Logger LOG = Logger.getLogger(getClass());

    @Inject
    protected CommissionRepository commissionRepository;

    @Inject
    protected Config config;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PaginatedResponse<Map<String, Object>> commesse() {
        LOG.info("traced");
        boolean refreshDefault = config.getRefreshDefault();
        MultivaluedMap<String, String> multivaluedMap = new MultivaluedHashMap<>();
        return commissionRepository.getCommesse(multivaluedMap,null,0,10,null);
    }
}