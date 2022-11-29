package it.ictgroup.service.event;

import it.ictgroup.model.pojo.CustomObject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.concurrent.CompletableFuture;

@ApplicationScoped
public class EventProducer {

    @Inject
    @Channel("event-out")
    Emitter<CustomObject> emitter;

    public Response send(CustomObject customObject) {
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



}
