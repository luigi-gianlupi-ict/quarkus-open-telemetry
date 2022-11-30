package it.ictgroup.resource;


import it.ictgroup.client.dataset.DataSetClientFactory;
import it.ictgroup.client.dataset.ServiceClient;
import it.ictgroup.config.service.Config;
import it.ictgroup.model.pojo.PaginatedResponse;
import it.ictgroup.service.elastic.CommissionRepository;
import org.jboss.logging.Logger;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.*;
import java.lang.reflect.Method;
import java.security.Principal;
import java.util.Map;

@Path("/traced")
@RolesAllowed("Asset")
public class TracedResource {
    final Logger LOG = Logger.getLogger(getClass());

    @Inject
    protected CommissionRepository commissionRepository;

    @Inject
    protected Config config;

    @Context
    ResourceInfo resourceInfo;

    @Context
    SecurityContext security;

    protected final DataSetClientFactory dataSetClient = DataSetClientFactory.builder().build();
    protected final ServiceClient serviceClient = dataSetClient.serviceClient();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PaginatedResponse<Map<String, Object>> commesse() {

        Principal userp = security.getUserPrincipal();
        userp.getName();
        Method methodInvoked = resourceInfo.getResourceMethod();
        String methodCalled = methodInvoked.toGenericString();
        Class<?> resourceClass = resourceInfo.getResourceClass();

        LOG.info("TracedResource: /traced called");
        boolean refreshDefault = config.getRefreshDefault();
        LOG.infof("TracedResource: refreshDefault = %b", refreshDefault);
        // TO DATA SET -> MATRIX
        //serviceClient.recordFindByCode("tipologiefamiglieflotte", "M", null).orElse(null);
        MultivaluedMap<String, String> multivaluedMap = new MultivaluedHashMap<>();
        return commissionRepository.getCommesse(multivaluedMap,null,0,10,null);
    }
}