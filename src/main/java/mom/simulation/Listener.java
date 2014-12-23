package mom.simulation;

import java.util.HashSet;
import java.util.Set;
import mom.dao.EventDao;
import mom.event.Event;
import mom.subscriber.Subscriber;

public class Listener implements Runnable {
    private final Subscriber subscriber;
    private final EventDao eventDao;
    private final int eventLimit;
    private Set<Event> events;

    public Listener(Subscriber subscriber, EventDao eventDao, int eventLimit) {
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
