package it.ictgroup.config.api;

import it.ictgroup.config.service.Config;
import it.ictgroup.utils.LogServiceMessageBuilder;
import it.ictgroup.utils.response.ServiceResponseBuilder;
import org.jboss.logging.Logger;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Map;


@Path("config")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@PermitAll
public class ConfigResource {

    final Logger LOG = Logger.getLogger(getClass());

    @Inject
    Config config;

   /* @Inject
    CamelBootstrap camelBootstrap;*/

    @GET
    @Path("_refresh")
    @PermitAll
    public Response refreshConfig(@Context UriInfo uriInfo) {
        LOG.info("Refreshing config...");
        config.refreshConfig();
        LOG.info("Config refreshed!");
        ServiceResponseBuilder<String> responseBuilder = new ServiceResponseBuilder<>();
        responseBuilder.message(LogServiceMessageBuilder.success(null, "Config refreshed!"));
        return Response.ok(responseBuilder.build()).build();
    }

    @GET
    @Path("_get")
    @PermitAll
    public Response getAllConfig(@Context UriInfo uriInfo) {
        Map<String, Object> params = config.getAllConfig();
        ServiceResponseBuilder<String> responseBuilder = new ServiceResponseBuilder<>();
        params.forEach((k, v) -> responseBuilder.message(LogServiceMessageBuilder.info(null, k + " = " + v)));
        return Response.ok(responseBuilder.build()).build();
    }

   /* @GET
    @Path("camel/_restart")
    @PermitAll
    public Response restartCamel(@Context UriInfo uriInfo) {
        LOG.log(Level.INFO, "Refreshing config...");
        config.refreshConfig();
        LOG.log(Level.INFO, "Config refreshed!");

        LOG.log(Level.INFO, "Restarting camel...");
        camelBootstrap.stop();
        camelBootstrap.start();
        LOG.log(Level.INFO, "Camel restarted!");ServiceResponseBuilder<String> responseBuilder = new ServiceResponseBuilder<>();
        responseBuilder.message(LogServiceMessageBuilder.success(null, "Camel restarted!"));
        return Response.ok(responseBuilder.build().asMap()).build();
    }*/
}
