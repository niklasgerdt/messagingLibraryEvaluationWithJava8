package mom.eventservice;

import java.util.Set;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import mom.net.JeroMqNetworkContext;
import mom.util.KillSwitch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.zeromq.ZMQ;

@Component
@Profile("jero")
@PropertySource("classpath:basic_simulation.properties")
public class JeroMqEventService implements EventService {
    private final static Logger logger = LoggerFactory.getLogger(JeroMqEventService.class);
    @Autowired(required = true)
    private JeroMqNetworkContext context;
    private final Set<String> inAddresses;
    @Value("${eventservice.jero.out}")
    private String outAddress;
    @Autowired
    private final KillSwitch killSwitch;
    private ZMQ.Socket sub;
    private ZMQ.Socket pub;

    public JeroMqEventService(JeroMqNetworkContext context, String outAddress, Set<String> inAddresses,
            KillSwitch killSwitch) {
        this.context = context;
        this.outAddress = outAddress;
        this.inAddresses = inAddresses;
        this.killSwitch = killSwitch;
    }

    @Override
    public void run() {
        logger.info("running event service");
        while (!killSwitch.isSet()) {
            String msg = sub.recvStr();
            if (!killSwitch.isSet())
                pub.send(msg);
        }
        logger.info("stopping event service");
    }

    @PostConstruct
    public void up() {
        logger.info("setting up event service (in({}), out({}))", inAddresses, outAddress);
        sub = context.socket(ZMQ.SUB);
        inAddresses.forEach(s -> sub.connect(s));
        sub.subscribe("".getBytes());
        pub = context.socket(ZMQ.PUB);
        pub.bind(outAddress);
    }

    @PreDestroy
    public void tearDown() {
        sub.close();
        pub.close();
    }
}
