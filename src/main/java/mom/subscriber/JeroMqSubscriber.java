package mom.subscriber;

import mom.net.NetworkContext;
import mom.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.zeromq.ZMQ;

public class JeroMqSubscriber extends Subscriber implements InitializingBean, DisposableBean {
    private final static Logger logger = LoggerFactory.getLogger(JeroMqSubscriber.class);
    private NetworkContext context;
    private final String address;
    private Socket socket;

    public JeroMqSubscriber(NetworkContext networkContext, String address) {
        this.context = networkContext;
        this.address = address;
    }

    @Override
    public String receive() {
        byte[] bytes = socket.recv();
        return new String(bytes);
    }

    @Override
    public void afterPropertiesSet() {
        logger.info("setting up subscriber {}", address);
        socket = context.socket(ZMQ.SUB);
        socket.connect(address);
    }

    @Override
    public void destroy() {
        socket.close();
    }
}
