package moma.net;

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

    public Socket socket(int socketType) {
        switch (socketType) {
        case ZMQ.PUB:
            return new Socket(ctx.socket(socketType));
        case ZMQ.SUB:
            return new Socket(ctx.socket(socketType));
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
        ctx.term();
    }
}
