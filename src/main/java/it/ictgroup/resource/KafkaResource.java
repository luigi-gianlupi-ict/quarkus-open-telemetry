package it.ictgroup.resource;

import it.ictgroup.model.pojo.CustomObject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.CompletableFuture;

@Path("/kafka")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class KafkaResource {

    final Logger LOG = Logger.getLogger(getClass());

   /* @Inject
    @Channel("event")
    Emitter<Long> emitter;*/

    @Inject
    @Channel("event-out")
    Emitter<CustomObject> emitter;

    @GET
    @Path("/send")
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendEventToKafka() {
        CustomObject customObject = new CustomObject("Luigi","Gianlupi");
        emitter.send(Message.of(customObject)
                .withAck(() -> {
                    // Called when the message is acked
                    return CompletableFuture.completedFuture(null);
                })
                .withNack(throwable -> {
                    // Called when the message is nacked
                    return CompletableFuture.completedFuture(null);
                }));
        return Response.accepted().build();
    }

    /*@GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response enqueueMovie() {
        emitter.send(Long.valueOf(432143));
        return Response.accepted().build();
    }*/

}