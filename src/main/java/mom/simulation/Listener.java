package mom.simulation;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import lombok.NonNull;
import mom.config.ActiveListenerConfiguration;
import mom.dao.EventDao;
import mom.event.Event;
import mom.subscriber.Subscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final static Logger logger = LoggerFactory.getLogger(Listener.class);
    private final Subscriber subscriber;
    private final EventDao eventDao;
    private final int eventLimit;
    private final EndSimulation ending;
    private Set<Event> events;
    private final int id;

    @Autowired
    public Listener(@NonNull final Subscriber subscriber, @NonNull final EventDao eventDao,
            @Value("${eventlimit}") final int eventLimit,
            @NonNull final ActiveListenerConfiguration listenerConfiguration, @NonNull final EndSimulation endSimulation) {
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
        while (true) {
            Optional<Event> e = subscriber.receive();
            e.ifPresent(ev ->
            {
                ev.setNanoReceived(System.nanoTime());
                logger.debug("received.event {}", ev);
                events.add(ev);
            });
            if (!e.isPresent() && ending.isEnded()) {
                logger.debug("ending listener, no more events");
                break;
            }
        }
        eventDao.insertAll(events);
    }
}
