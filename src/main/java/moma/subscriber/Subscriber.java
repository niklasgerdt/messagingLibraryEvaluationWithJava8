package moma.subscriber;

import com.google.gson.Gson;
import mom.event.Event;

public abstract class Subscriber {
    private final Gson gson = new Gson();

    public Event receiveEvent() {
        String s = receive();
        Event e = gson.fromJson(s, Event.class);
        return e;
    }

    public abstract String receive();
}
