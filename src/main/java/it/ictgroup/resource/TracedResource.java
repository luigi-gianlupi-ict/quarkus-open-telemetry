package it.ictgroup.resource;


import it.ictgroup.asset.client.dataset.DataSetClientFactory;
import it.ictgroup.asset.client.dataset.ServiceClient;
import it.ictgroup.asset.framework.auth.AuthAccessElement;
import it.ictgroup.asset.framework.auth.CommissionElement;
import it.ictgroup.asset.framework.auth.entity.HeaderConstant;
import it.ictgroup.config.service.Config;
import it.ictgroup.model.pojo.PaginatedResponse;
import it.ictgroup.service.elastic.CommissionRepository;
import org.jboss.logging.Logger;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.util.Map;

@Path("/traced")
//@RolesAllowed("Asset")
//@Authenticated
//@ApiFunction
public class TracedResource extends AuthenticatedResource {
    final Logger LOG = Logger.getLogger(getClass());

    @Inject
    protected CommissionRepository commissionRepository;

    @Inject
    protected Config config;

    protected final DataSetClientFactory dataSetClient = DataSetClientFactory.builder().build();
    protected final ServiceClient serviceClient = dataSetClient.serviceClient();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public PaginatedResponse<Map<String, Object>> commesse(@Context UriInfo uriInfo,
                                                           @Context HttpHeaders httpHeaders,
                                                           @HeaderParam(HeaderConstant.CUSTOMER) String customer,
                                                           @HeaderParam(CommissionElement.PARAM_COMMISSION) String commission,
                                                           @HeaderParam(AuthAccessElement.PARAM_AUTH_ID) String user,
                                                           @HeaderParam(AuthAccessElement.PARAM_AUTH_GROUP) String group,
                                                           @HeaderParam(HeaderConstant.PROFILING_GROUPS) String profilingGroups,
                                                           @HeaderParam(HeaderConstant.FILTERS) String groupFilters) {

        LOG.info("TracedResource: /traced called");
        boolean refreshDefault = config.getRefreshDefault();
        LOG.infof("TracedResource: refreshDefault = %b", refreshDefault);
        // TO DATA SET -> MATRIX
        //serviceClient.recordFindByCode("tipologiefamiglieflotte", "M", null).orElse(null);
        MultivaluedMap<String, String> multivaluedMap = new MultivaluedHashMap<>();
        return commissionRepository.getCommesse(multivaluedMap,null,0,10,null);
    }
}