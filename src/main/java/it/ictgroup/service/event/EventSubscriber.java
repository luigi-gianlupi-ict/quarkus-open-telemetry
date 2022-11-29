package it.ictgroup.service.event;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import it.ictgroup.model.pojo.CustomObject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.jboss.logging.Logger;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class EventSubscriber {

    final Logger LOG = Logger.getLogger(getClass());

    @Inject
    @Channel("my-data-stream")
    Publisher<CustomObject> customObjectPublisher;


    void onStart(@Observes StartupEvent ev) {
        //customObjectPublisher.subscribe(customObjectSubscriber);
        LOG.info("The application is starting...");
    }

    void onStop(@Observes ShutdownEvent ev) {
        LOG.info("The application is stopping...");
    }

    Subscriber<CustomObject> customObjectSubscriber = new Subscriber<>() {
        private Subscription subscription;

        @Override
        public void onSubscribe(Subscription subscription) {
            this.subscription = subscription;
            this.subscription.request(1);
        }

        @Override
        public void onNext(CustomObject customObject) {
            System.out.println("[Subscriber] : " + Thread.currentThread().getName() + " event : " + customObject.getName());
            this.subscription.request(1);
        }

        @Override
        public void onError(Throwable throwable) {
        }

        @Override
        public void onComplete() {
            System.out.println("onComplete");
        }
    };



}
