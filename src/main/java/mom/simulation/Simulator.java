package mom.simulation;

import java.util.stream.Stream;
import lombok.NonNull;
import mom.event.EventSource;
import mom.publisher.Publisher;
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
public class Simulator implements Runnable {
    private final static Logger logger = LoggerFactory.getLogger(Simulator.class);
    private final EventSource source;
    private final Publisher publisher;
    private final EndSimulation ending;
    private final int limit;

    @Autowired
    public Simulator(@NonNull final EventSource eventSource, @NonNull final Publisher publisher,
            @Value("${eventlimit}") final int limit, @NonNull EndSimulation endSimulation) {
        this.source = eventSource;
        this.publisher = publisher;
        this.limit = limit;
        this.ending = endSimulation;
    }

    public void simulate() {
        Stream.generate(source).limit(limit).filter(e -> !ending.isEnded()).peek(e -> logger.debug("sending.event {}", e)).forEach(e -> publisher.pub(e.get()));
    }

    @Override
    public void run() {
        simulate();
    }
}
