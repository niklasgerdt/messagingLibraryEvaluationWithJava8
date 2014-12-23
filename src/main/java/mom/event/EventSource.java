package mom.event;

import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;
import mom.util.Pauser;

public class EventSource implements Supplier<Optional<Event>> {
    private final int id;
    private final int limit;
    private final Pauser pauser;
    private final String content;
    private int eventId = 0;

    public EventSource(int id, int msgLen, int limit, Pauser pauser) {
        this.id = id;
        this.limit = limit;
        this.pauser = pauser;
        Random r = new Random();
        byte[] bytes = new byte[msgLen];
        r.nextBytes(bytes);
        content = bytes.toString();
    }

    @Override
    public Optional<Event> get() {
        pauser.pause();
        eventId++;
        if (eventId >= limit)
            return Optional.empty();
        else
            return Optional.of(createEvent());
    }

    private Event createEvent() {
        return new Event(id, content);
    }
}
