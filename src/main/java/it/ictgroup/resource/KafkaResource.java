package it.ictgroup.resource;

import it.ictgroup.model.pojo.CustomObject;
import it.ictgroup.service.event.EventProducer;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/kafka")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class KafkaResource {

    final Logger LOG = Logger.getLogger(getClass());

    @Inject
    protected EventProducer eventProducer;

    @GET
    @Path("/send")
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendEventToKafka() {
        CustomObject customObject = new CustomObject("Luigi","Gianlupi");
        return eventProducer.send(customObject);
    }


}