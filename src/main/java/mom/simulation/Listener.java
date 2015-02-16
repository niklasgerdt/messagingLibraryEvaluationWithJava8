package mom.simulation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.NonNull;
import mom.config.simulation.ActiveListenerConfiguration;
import mom.event.Event;
import mom.event.EventDao;
import mom.event.InMemoryEventDao;
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
    private static final int TOTALEVENTS = 10_000_000;
    private final static Logger logger = LoggerFactory.getLogger(Listener.class);
    private final Subscriber subscriber;
    private final EventDao eventDao;
    private final EndSimulation ending;
    private Event[] events;
    private int eventPointer;
    private final int id;

    @Autowired
    public Listener(@NonNull final Subscriber subscriber, @NonNull final EventDao eventDao,
            @NonNull final ActiveListenerConfiguration listenerConfiguration, @NonNull final EndSimulation endSimulation) {
        this.subscriber = subscriber;
        this.eventDao = eventDao;
        this.id = listenerConfiguration.getId();
        ending = endSimulation;
        ending.activateListener();
        events = new Event[TOTALEVENTS];
        eventPointer = 0;
        logger.info("initialised listener sub {}, dao {}, limit {}, id {}", subscriber, eventDao, id);
    }

    @Override
    public void run() {
        while (true) {
            Optional<Event> e = subscriber.receive();
            e.ifPresent(ev ->
            {
                // logger.debug("received e: {}", ev);
                ev.setNanoReceived(System.nanoTime());
                ev.setReceiverId(id);
                // logger.debug("received.event {}", ev);
                events[eventPointer++] = ev;
            });
            if (!e.isPresent() && ending.isSimulationsEnded()) {
                logger.info("ending listener, no more events");
                break;
            }
        }
        logger.info("cached events {}", eventPointer);
        Event[] truncEvents = Arrays.copyOf(events, eventPointer);
        List<Event> eventList = Arrays.asList(truncEvents);
        Set<Event> eventSet = new HashSet<>(eventList);
        hackLog(eventSet);
        eventDao.insertAll(eventSet);
        logger.info("writed events {} to db ", eventList.size());
        ending.deActivateListener();
    }

    // TODO fix this (tear)
    private void hackLog(Set<Event> events) {
        EventDao ed = new InMemoryEventDao();
        ed.insertAll(events);
        int from = 500_000;
        int to = 1500_000;
        ed.listenerStatistics(from, to);
        logger.info("avg: {}:{}", id, ed.getRoutingAverageTime(from, to));
        logger.info("dev: {}:{}", id, ed.getRoutingStandardDeviation(from, to));
        logger.info("min: {}:{}", id, ed.getRoutingMinTime(from, to));
        logger.info("max: {}:{}", id, ed.getRoutingMaxTime(from, to));
    }
}
