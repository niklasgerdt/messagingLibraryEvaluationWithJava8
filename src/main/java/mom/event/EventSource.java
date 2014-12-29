package mom.event;

import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;
import mom.util.Pauser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventSource implements Supplier<Optional<Event>> {
    private final static Logger logger = LoggerFactory.getLogger(EventSource.class);
    private final int id;
    private final Pauser pauser;
    private final String content;
    private int eventId = 0;

    public EventSource(int id, int msgLen, Pauser pauser) {
        this.id = id;
        this.pauser = pauser;
        Random r = new Random();
        byte[] bytes = new byte[msgLen];
        r.nextBytes(bytes);
        content = bytes.toString();
        logger.info("created new event source {}", id);
    }

    @Override
    public Optional<Event> get() {
        pauser.pause();
        eventId++;
        return Optional.of(createEvent());
    }

    private Event createEvent() {
        return new Event(id, eventId, content);
    }
}
