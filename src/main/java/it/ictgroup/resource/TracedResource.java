package it.ictgroup.resource;



import io.vertx.core.http.HttpServerRequest;
/*import it.ictgroup.asset.client.dataset.DataSetClientFactory;
import it.ictgroup.asset.client.dataset.ServiceClient;*/
import it.ictgroup.config.service.Config;
import it.ictgroup.model.pojo.PaginatedResponse;
import it.ictgroup.service.elastic.CommissionRepository;
import org.jboss.logging.Logger;
import org.keycloak.KeycloakSecurityContext;

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
//@RolesAllowed("Asset")
//@Authenticated
public class TracedResource extends AuthenticatedResource {
    final Logger LOG = Logger.getLogger(getClass());

    @Inject
    protected CommissionRepository commissionRepository;

    @Inject
    protected Config config;

    //protected final DataSetClientFactory dataSetClient = DataSetClientFactory.builder().build();
    //protected final ServiceClient serviceClient = dataSetClient.serviceClient();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PaginatedResponse<Map<String, Object>> commesse() {

        LOG.info("TracedResource: /traced called");
        boolean refreshDefault = config.getRefreshDefault();
        LOG.infof("TracedResource: refreshDefault = %b", refreshDefault);
        // TO DATA SET -> MATRIX
        //serviceClient.recordFindByCode("tipologiefamiglieflotte", "M", null).orElse(null);
        MultivaluedMap<String, String> multivaluedMap = new MultivaluedHashMap<>();
        return commissionRepository.getCommesse(multivaluedMap,null,0,10,null);
    }
}