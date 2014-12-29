package moma.event;

import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;
import javax.annotation.PostConstruct;
import moma.util.Pauser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@PropertySource("classpath:basic_simulation.properties")
public class EventSource implements Supplier<Optional<Event>> {
    private final static Logger logger = LoggerFactory.getLogger(EventSource.class);
    private static int ID = 0;
    private final int id = ID++;
    @Autowired
    private Pauser pauser;
    @Value("${eventlength}")
    private int eventlength;
    private String content;
    private int eventId = 0;

    @PostConstruct
    public void createEventContent() {
        Random r = new Random();
        byte[] bytes = new byte[eventlength];
        r.nextBytes(bytes);
        content = bytes.toString();
        logger.info("created new event source {}", id);
    }

    @Override
    public Optional<Event> get() {
        pauser.pause();
        eventId++;
        return Optional.of(new Event(id, eventId, content));
    }
}
