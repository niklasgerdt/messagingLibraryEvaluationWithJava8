package mom.simulation;

import java.util.stream.Stream;
import mom.event.EventSource;
import mom.publisher.Publisher;
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
    private final EventSource source;
    private final Publisher publisher;
    private final int limit;

    @Autowired
    public Simulator(final EventSource eventSource, final Publisher publisher, @Value("${eventlimit}") final int limit) {
        this.source = eventSource;
        this.publisher = publisher;
        this.limit = limit;
    }

    public void simulate() {
        Stream.generate(source).limit(limit).forEach(e -> publisher.pub(e.get()));
    }

    @Override
    public void run() {
        simulate();
    }
}
