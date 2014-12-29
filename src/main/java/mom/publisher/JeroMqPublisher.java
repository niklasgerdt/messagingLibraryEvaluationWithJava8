package mom.publisher;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import mom.event.Event;
import mom.net.JeroMqNetworkContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zeromq.ZMQ;
import com.google.gson.Gson;

public class JeroMqPublisher implements Publisher {
    private final static Logger logger = LoggerFactory.getLogger(JeroMqPublisher.class);
    private final Gson gson = new Gson();
    @Autowired
    private JeroMqNetworkContext context;
    private final String address;
    private ZMQ.Socket socket;

    @Autowired
    public JeroMqPublisher(JeroMqNetworkContext networkContext, String address) {
        this.context = networkContext;
        this.address = address;
    }

    @Override
    public void pub(Event e) {
        String notification = gson.toJson(e);
        socket.send(notification);
    }

    @PostConstruct
    public void afterPropertiesSet() throws Exception {
        logger.info("binding publisher socket to address {}", address);
        socket = context.socket(ZMQ.PUB);
        socket.bind(address);
    }

    @PreDestroy
    public void destroy() throws Exception {
        logger.info("destroying publisher {}", address);
        socket.close();
    }
}
