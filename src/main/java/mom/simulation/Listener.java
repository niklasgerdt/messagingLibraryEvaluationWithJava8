package mom.simulation;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import lombok.NonNull;
import mom.config.simulation.ActiveListenerConfiguration;
import mom.event.Event;
import mom.event.EventDao;
import mom.simulation.sut.Subscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final EndSimulation ending;
    private Set<Event> events;
    private final int id;

    @Autowired
    public Listener(@NonNull final Subscriber subscriber, @NonNull final EventDao eventDao,
            @NonNull final ActiveListenerConfiguration listenerConfiguration, @NonNull final EndSimulation endSimulation) {
        this.subscriber = subscriber;
        this.eventDao = eventDao;
        this.id = listenerConfiguration.getId();
        ending = endSimulation;
        ending.activateListener();
        events = new HashSet<>(10_000_000);
        logger.info("initialised listener sub {}, dao {}, limit {}, id {}", subscriber, eventDao, id);
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
            if (!e.isPresent() && ending.isSimulationsEnded()) {
                logger.info("ending listener, no more events");
                break;
            }
        }
        logger.info("writing events {} to db ", events.size());
        eventDao.insertAll(events);
        logger.info("writed events {} to db ", events.size());
        ending.deActivateListener();
    }
}
