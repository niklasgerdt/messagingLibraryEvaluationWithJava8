package mom.net;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.zeromq.ZMQ;

@Component
@Profile({ "zero", "default" })
public class NetworkContext {
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

    @PreDestroy
    private void term() {
        sockets.forEach(s -> s.close());
        ctx.close();
    }
}
