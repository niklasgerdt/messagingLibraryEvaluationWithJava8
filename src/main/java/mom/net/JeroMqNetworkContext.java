package mom.net;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;

public class JeroMqNetworkContext {
    private static final int ZMQ_THREADS = 1;
    private final ZMQ.Context ctx;

    public JeroMqNetworkContext() {
        ctx = ZMQ.context(ZMQ_THREADS);
    }

    public Socket socket(int socketType) {
        switch (socketType) {
        case ZMQ.PUB:
            return ctx.socket(socketType);
        case ZMQ.SUB:
            return ctx.socket(socketType);
        default:
            throw new IllegalArgumentException();
        }
    }

    public void term() {
        ctx.term();
    }
}
