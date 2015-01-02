package mom.simulation.sut.jeromq;

import java.util.Optional;
import javax.annotation.PostConstruct;
import mom.event.Event;
import mom.simulation.sut.Subscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zeromq.ZMQ;
import com.google.gson.Gson;

@Component
@Scope("prototype")
@Profile({ "zero", "default" })
@Lazy
@PropertySource("classpath:/basic_simulation.properties")
public class JeroMqSubscriber implements Subscriber {
    private final static Logger logger = LoggerFactory.getLogger(JeroMqSubscriber.class);
    private final Gson gson = new Gson();
    private final NetworkContext context;
    private final String address;
    private Socket socket;

    @Autowired
    public JeroMqSubscriber(final NetworkContext networkContext,
            @Value("${eventServiceOutputAddress}") final String address) {
        logger.info("creating subscriber {}", address);
        this.context = networkContext;
        this.address = address;
    }

    @Override
    public Optional<Event> receive() {
        logger.trace("receiving raw event");
        String raw = socket.recvStr();
        logger.trace("received raw event {}", raw);
        if (raw == null) {
            return Optional.empty();
        } else {
            Event e = gson.fromJson(raw, Event.class);
            logger.trace("converted raw event to object {}", e);
            return Optional.of(e);
        }
    }

    @PostConstruct
    public void afterPropertiesSet() {
        logger.info("setting up subscriber {}", address);
        socket = context.socket(ZMQ.SUB);
        socket.connect(address);
        socket.subscribe("".getBytes());
        logger.info("connected {}", address);
    }
}
