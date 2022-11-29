package it.ictgroup.service.event;

import io.smallrye.reactive.messaging.annotations.Broadcast;
import it.ictgroup.model.pojo.CustomObject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.jboss.logging.Logger;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * A bean consuming data from the "event" Kafka topic and applying some conversion.
 * The result is pushed to the "my-data-stream" stream which is an in-memory stream.
 */
@ApplicationScoped
public class EventConsumer {

    final Logger LOG = Logger.getLogger(getClass());

    @Incoming("event-in")
    @Outgoing("my-data-stream")
    //@Broadcast
    public CustomObject process(CustomObject customObject) {
        LOG.infof("Custom Object %s %s", customObject.getName(),customObject.getSurname());
        customObject.setName("Mario");
        return customObject;
    }




}