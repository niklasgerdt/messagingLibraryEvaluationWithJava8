package mom.publisher;

import mom.net.JeroMqNetworkContext;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.zeromq.ZMQ;

public class JeroMqPublisher extends Publisher implements InitializingBean, DisposableBean {
    private JeroMqNetworkContext context;
    private final String address;
    private ZMQ.Socket socket;

    public JeroMqPublisher(JeroMqNetworkContext networkContext, String address) {
        super();
        this.context = networkContext;
        this.address = address;
    }

    @Override
    public void pub(String notification) {
        socket.send(notification);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        socket = context.socket(ZMQ.PUB);
        socket.bind(address);
    }

    @Override
    public void destroy() throws Exception {
        socket.close();
        context.term();
    }
}
