package moma.publisher;

import moma.event.Event;

public interface Publisher {

    public void bind(String address);

    public void pub(Event e);
}
