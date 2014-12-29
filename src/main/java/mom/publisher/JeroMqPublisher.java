package mom.publisher;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import mom.net.JeroMqNetworkContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.zeromq.ZMQ;

@Component
@PropertySource("classpath:")
public class JeroMqPublisher extends Publisher {
    private final static Logger logger = LoggerFactory.getLogger(JeroMqPublisher.class);
    private JeroMqNetworkContext context;
    private final String address;
    private ZMQ.Socket socket;

    @Autowired
    public JeroMqPublisher(JeroMqNetworkContext networkContext, String address) {
        super();
        this.context = networkContext;
        this.address = address;
    }

    @Override
    public void pub(String notification) {
        socket.send(notification);
    }

    @PostConstruct
    public void afterPropertiesSet() throws Exception {
        logger.info("binding publisher socket to address %s", address);
        socket = context.socket(ZMQ.PUB);
        socket.bind(address);
    }

    @PreDestroy
    public void destroy() throws Exception {
        logger.info("destroying socket");
        socket.close();
    }
}
