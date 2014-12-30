package mom.publisher;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import mom.event.Event;
import mom.net.NetworkContext;
import mom.net.OutputAddressFactory;
import mom.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zeromq.ZMQ;
import com.google.gson.Gson;

@Component
@Scope("prototype")
@Profile({ "zero", "default" })
public class JeroMqPublisher implements Publisher {
    private final static Logger logger = LoggerFactory.getLogger(JeroMqPublisher.class);
    private final Gson gson = new Gson();
    private final NetworkContext ctx;
    private final OutputAddressFactory addressFactory;
    private Socket socket;

    @Autowired
    public JeroMqPublisher(final NetworkContext ctx, final OutputAddressFactory addressFactory) {
        this.ctx = ctx;
        this.addressFactory = addressFactory;
    }

    @Override
    public void pub(Event e) {
        String notification = gson.toJson(e);
        socket.send(notification);
    }

    @PostConstruct
    public void bind() {
        String address = addressFactory.getNextAddress();
        logger.info("binding publisher socket to address {}", address);
        socket = ctx.socket(ZMQ.PUB);
        socket.bind(address);
    }

    @PreDestroy
    public void destroy() throws Exception {
        logger.info("destroying publisher");
        socket.close();
    }
}
