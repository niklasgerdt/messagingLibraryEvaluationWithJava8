package mom.simulation;

import java.util.stream.Stream;
import mom.event.EventSource;
import mom.publisher.Publisher;

public class Simulator implements Runnable {
    private final EventSource source;
    private final Publisher publisher;

    public Simulator(EventSource eventSource, Publisher publisher) {
        this.source = eventSource;
        this.publisher = publisher;
    }

    public void simulate() {
        Stream.generate(source).filter(e -> e.isPresent()).forEach(e -> publisher.pub(e.get()));
    }

    @Override
    public void run() {
        simulate();
    }
}
