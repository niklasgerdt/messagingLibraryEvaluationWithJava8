package mom.simulation.sut.jeromq;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.zeromq.ZMQ;

@Component
@Profile({ "zero", "jero", "jerotest", "default" })
public class NetworkContext {
    final static Logger logger = LoggerFactory.getLogger(NetworkContext.class);
    private static final int ZMQ_THREADS = 1;
    private ZMQ.Context ctx;
    private final List<Socket> sockets = new CopyOnWriteArrayList<>();

    public Socket socket(int socketType) {
        switch (socketType) {
        case ZMQ.PUB:
            Socket pub = new Socket(ctx.socket(socketType));
            sockets.add(pub);
            return pub;
        case ZMQ.SUB:
            Socket sub = new Socket(ctx.socket(socketType));
            sockets.add(sub);
            return sub;
        default:
            throw new IllegalArgumentException();
        }
    }

    @PostConstruct
    public void init() {
        ctx = ZMQ.context(ZMQ_THREADS);
    }

    public void term() {
        logger.info("destroying sockets {}");
        sockets.forEach(s -> s.close());
        logger.info("destroyed sockets {}", sockets);
        logger.info("destroying context");
        ctx.close();
        logger.info("destroyed context");
    }
}
