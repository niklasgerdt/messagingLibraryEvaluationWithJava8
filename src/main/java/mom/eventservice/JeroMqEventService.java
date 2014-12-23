package mom.eventservice;

import java.util.Set;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import mom.util.KillSwitch;
import org.zeromq.ZMQ;

public class JeroMqEventService implements EventService {
    private final ZMQ.Context ctx = ZMQ.context(1);
    private final ZMQ.Socket sub = ctx.socket(ZMQ.SUB);
    private final ZMQ.Socket pub = ctx.socket(ZMQ.PUB);
    private final Set<String> inAddresses;
    private final String outAddress;
    private final KillSwitch killSwitch;

    public JeroMqEventService(String outAddress, Set<String> inAddresses, KillSwitch killSwitch) {
        this.outAddress = outAddress;
        this.inAddresses = inAddresses;
        this.killSwitch = killSwitch;
    }

    @Override
    public void run() {
        while (!killSwitch.isSet()) {
            String msg = sub.recvStr();
            pub.send(msg);
        }
    }

    @PostConstruct
    public void up(String address) {
        inAddresses.forEach(s -> sub.connect(s));
        sub.subscribe("".getBytes());
        sub.setReceiveTimeOut(1);
        pub.bind(outAddress);
    }

    @PreDestroy
    public void tearDown() {
        sub.close();
        pub.close();
        ctx.close();
    }
}
