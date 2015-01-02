package mom.subscriber;

import java.util.Optional;
import mom.event.Event;

public interface Subscriber {

    Optional<Event> receive();

}
