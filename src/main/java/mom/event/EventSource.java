package mom.event;

import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;
import javax.annotation.PostConstruct;
import mom.config.SimulationConfiguration;
import mom.util.Pauser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Lazy
public class EventSource implements Supplier<Optional<Event>> {
    private final static Logger logger = LoggerFactory.getLogger(EventSource.class);
    private final int id;
    private final Pauser pauser;
    private final int eventlength;
    private String content;
    private int eventId = 0;

    @Autowired
    public EventSource(Pauser pauser, SimulationConfiguration simulationConfiguration) {
        this.pauser = pauser;
        this.id = simulationConfiguration.getSimulators().get(0).getSimulatorId();
        this.eventlength = simulationConfiguration.getSimulators().get(0).getEventContentLength();
    }

    @Override
    public Optional<Event> get() {
        pauser.pause();
        eventId++;
        return Optional.of(new Event(id, eventId, content));
    }

    @PostConstruct
    public void createEventContent() {
        Random r = new Random();
        byte[] bytes = new byte[eventlength];
        r.nextBytes(bytes);
        content = bytes.toString();
        logger.info("created new event content {}", content);
    }
}
