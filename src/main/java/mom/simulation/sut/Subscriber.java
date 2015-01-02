package mom.simulation.sut;

import java.util.Optional;
import mom.event.Event;

public interface Subscriber {

    Optional<Event> receive();

}
