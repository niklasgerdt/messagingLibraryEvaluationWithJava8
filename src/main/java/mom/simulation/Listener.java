package mom.simulation;

import java.util.HashSet;
import java.util.Set;
import mom.dao.EventDao;
import mom.event.Event;
import mom.subscriber.Subscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Lazy
@PropertySource("classpath:/basic_simulation.properties")
public class Listener implements Runnable {
    private final Subscriber subscriber;
    private final EventDao eventDao;
    private final int eventLimit;
    private Set<Event> events;

    @Autowired
    public Listener(Subscriber subscriber, EventDao eventDao, @Value("${eventlimit}") final int eventLimit) {
        this.subscriber = subscriber;
        this.eventDao = eventDao;
        this.eventLimit = eventLimit;
        events = new HashSet<>(eventLimit);
    }

    @Override
    public void run() {
        Event e = subscriber.receiveEvent();
        events.add(e);
        if (events.size() == eventLimit)
            eventDao.insertAll(events);
    }
}
