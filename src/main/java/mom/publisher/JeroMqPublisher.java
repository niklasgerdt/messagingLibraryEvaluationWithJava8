package mom.publisher;

import javax.annotation.PreDestroy;
import mom.event.Event;
import mom.net.NetworkContext;
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
    @Autowired
    private NetworkContext ctx;
    private Socket socket;

    @Override
    public void bind(String address) {
        logger.info("binding publisher socket to address {}", address);
        socket = ctx.socket(ZMQ.PUB);
        socket.bind(address);
    }

    @Override
    public void pub(Event e) {
        String notification = gson.toJson(e);
        socket.send(notification);
    }

    @PreDestroy
    public void destroy() throws Exception {
        logger.info("destroying publisher");
        socket.close();
    }
}
