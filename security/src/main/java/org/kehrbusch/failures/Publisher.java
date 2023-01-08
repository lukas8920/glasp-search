package org.kehrbusch.failures;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class Publisher {
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public Publisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }

    public void publishAuthorizationFailure(){
        eventPublisher.publishEvent(new AuthorizationFailureEvent(this));
    }

    public void publishAuthorizationSuccess() {eventPublisher.publishEvent(new AuthorizationSuccessEvent(this));}

    public static class AuthorizationFailureEvent extends ApplicationEvent {
        public AuthorizationFailureEvent(Object source) {
            super(source);
        }
    }

    public static class AuthorizationSuccessEvent extends ApplicationEvent {
        public AuthorizationSuccessEvent(Object source) {
            super(source);
        }
    }
}
