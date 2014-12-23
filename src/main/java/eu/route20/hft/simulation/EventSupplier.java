package eu.route20.hft.simulation;

import java.util.*;
import java.util.function.*;
import lombok.*;
import mom.event.Event;
import eu.route20.hft.events.*;

public class EventSupplier<T> implements Supplier<Optional<Event>> {
    private final String message;
    private final int msgLen;

    public EventSupplier(int msgLen) {
        this.msgLen = msgLen;
        Random r = new Random();
        val bytes = new byte[msgLen];
        r.nextBytes(bytes);
        message = bytes.toString();
    }

    @Override
    public Optional<Event> get() {
        Event msg = null;
        if (Kill.killed())
            return Optional.empty();
        else
            return Optional.of(msg);
    }
}
