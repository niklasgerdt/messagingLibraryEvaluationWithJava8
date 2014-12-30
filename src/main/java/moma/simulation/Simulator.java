package moma.simulation;

import java.util.stream.Stream;
import moma.event.EventSource;
import moma.publisher.Publisher;

public class Simulator implements Runnable {
    private final EventSource source;
    private final Publisher publisher;
    private final int limit;

    public Simulator(EventSource eventSource, Publisher publisher, int limit) {
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
