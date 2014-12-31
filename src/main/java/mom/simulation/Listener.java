package mom.simulation;

import java.util.HashSet;
import java.util.Set;
import lombok.NonNull;
import mom.config.ActiveListenerConfiguration;
import mom.dao.EventDao;
import mom.event.Event;
import mom.subscriber.JeroMqSubscriber;
import mom.subscriber.Subscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Lazy
@Scope("prototype")
@PropertySource("classpath:/basic_simulation.properties")
public class Listener implements Runnable {
    private final static Logger logger = LoggerFactory.getLogger(JeroMqSubscriber.class);
    private final Subscriber subscriber;
    private final EventDao eventDao;
    private final int eventLimit;
    private final EndSimulation ending;
    private Set<Event> events;
    private final int id;

    @Autowired
    public Listener(@NonNull @Qualifier("subscriber") final Subscriber subscriber, @NonNull final EventDao eventDao,
            @Value("${eventlimit}") final int eventLimit,
            @Qualifier("activeListenerConfiguration") @NonNull final ActiveListenerConfiguration listenerConfiguration,
            @NonNull final EndSimulation endSimulation) {
        this.subscriber = subscriber;
        this.eventDao = eventDao;
        this.eventLimit = eventLimit;
        this.id = listenerConfiguration.getId();
        ending = endSimulation;
        events = new HashSet<>(eventLimit);
        logger.info("initialised listener sub {}, dao {}, limit {}, id {}", subscriber, eventDao, eventLimit, id);
    }

    @Override
    public void run() {
        while (!ending.isEnded()) {
            Event e = subscriber.receive();
            events.add(e);
            if (events.size() == eventLimit)
                eventDao.insertAll(events);
        }
        eventDao.insertAll(events);
    }
}
