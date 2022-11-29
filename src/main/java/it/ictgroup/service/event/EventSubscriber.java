package it.ictgroup.service.event;

import it.ictgroup.model.pojo.CustomObject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EventSubscriber {

    final Logger LOG = Logger.getLogger(getClass());

    @Incoming("my-data-stream")
    public void consume(CustomObject customObject) {
        LOG.info("[Subscriber] : " + Thread.currentThread().getName() + " event : " + customObject.getName());
    }

    @Incoming("my-data-stream")
    public void consume2(CustomObject customObject) {
        LOG.info("[Subscriber again] : " + Thread.currentThread().getName() + " event : " + customObject.getName());
    }


}
