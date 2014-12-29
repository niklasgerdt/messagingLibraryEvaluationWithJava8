package mom.util;

import java.io.IOException;
import mom.event.Event;
import mom.publisher.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KillSwitch implements Runnable {
    final static Logger logger = LoggerFactory.getLogger(KillSwitch.class);
    private boolean set = false;
    private final Publisher publisher;

    @Autowired
    public KillSwitch(Publisher publisher) {
        this.publisher = publisher;
    }

    public void waitForKill() {
        System.out.println("press any key to stop event service...");
        try {
            System.in.read();
            logger.info("end switch application..");
            set(true);
            Event e = new Event(0, "end event");
            publisher.pub(e);
        } catch (IOException e) {
            logger.info(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public boolean isSet() {
        return set;
    }

    public void set(boolean set) {
        this.set = set;
    }

    @Override
    public void run() {
        waitForKill();
    }
}
