package mom.publisher;

import mom.event.Event;

public interface Publisher {

    public void bind(String address);

    public void pub(Event e);
}
