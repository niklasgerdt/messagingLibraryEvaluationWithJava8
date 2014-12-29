package mom.subscriber;

import mom.net.JeroMqNetworkContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.zeromq.ZMQ;

public class JeroMqSubscriber extends Subscriber implements InitializingBean, DisposableBean {
    private final static Logger logger = LoggerFactory.getLogger(JeroMqSubscriber.class);
    private JeroMqNetworkContext context;
    private final String address;
    private ZMQ.Socket socket;

    public JeroMqSubscriber(JeroMqNetworkContext networkContext, String address) {
        this.context = networkContext;
        this.address = address;
    }

    @Override
    public String receive() {
        return socket.recvStr();
    }

    @Override
    public void afterPropertiesSet() {
        logger.info("setting up subscriber %s", address);
        socket = context.socket(ZMQ.SUB);
        socket.connect(address);
    }

    @Override
    public void destroy() {
        socket.close();
    }
}
