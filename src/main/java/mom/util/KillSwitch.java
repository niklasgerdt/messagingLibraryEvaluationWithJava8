package mom.util;

import java.io.IOException;
import mom.event.Event;
import mom.simulation.sut.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class KillSwitch implements Runnable {
    final static Logger logger = LoggerFactory.getLogger(KillSwitch.class);
    private volatile boolean set = false;
    private final Publisher publisher;

    @Autowired
    public KillSwitch(final Publisher publisher) {
        this.publisher = publisher;
    }

    public void waitForKill() {
        System.out.println("press any key to stop event service...");
        try {
            System.in.read();
            logger.info("end switch application..");
            setAndNotify(true);
        } catch (IOException e) {
            logger.info(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public boolean isSet() {
        return set;
    }

    public void setAndNotify(boolean set) {
        this.set = set;
        Event e = new Event(0, 0, "end event");
        publisher.pub(e);
    }

    @Override
    public void run() {
        waitForKill();
    }
}
