package moma.config;

import moma.subscriber.Subscriber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SubscriberFactory {
    private int addressSpace;

    @Bean
    public Subscriber subscriber() {
        return null;
    }
}
