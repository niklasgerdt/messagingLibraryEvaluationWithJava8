package mom.util;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KillSwitch implements Runnable {
    final static Logger logger = LoggerFactory.getLogger(KillSwitch.class);
    private boolean set = false;

    public void waitForKill() {
        System.out.println("press any key to stop event service...");
        try {
            System.in.read();
            logger.info("end switch application..");
            set(true);
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
