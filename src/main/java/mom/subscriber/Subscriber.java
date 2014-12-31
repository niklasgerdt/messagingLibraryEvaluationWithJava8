package mom.subscriber;

import mom.event.Event;

public interface Subscriber {

    Event receive();
}
