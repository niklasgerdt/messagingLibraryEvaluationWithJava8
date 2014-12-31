package mom.subscriber;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import mom.event.Event;
import mom.net.NetworkContext;
import mom.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
@Qualifier("subscriber")
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
    public Event receive() {
        String raw = socket.recvStr();
        Event e = gson.fromJson(raw, Event.class);
        return e;
    }

    @PostConstruct
    public void afterPropertiesSet() {
        logger.info("setting up subscriber {}", address);
        socket = context.socket(ZMQ.SUB);
        socket.connect(address);
        logger.info("connected {}", address);
    }

    @PreDestroy
    public void destroy() {
        socket.close();
    }
}
