package mom.util;

import mom.config.ActiveSimulatorConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Lazy
public class Pauser {
    private final static Logger logger = LoggerFactory.getLogger(Pauser.class);
    private final int nanoPause;

    @Autowired
    public Pauser(@Qualifier("activeSimulatorConfiguration") ActiveSimulatorConfiguration conf) {
        this.nanoPause = conf.getPauseBetweenEvents();
    }

    // Busy wait for granularity
    public void pause() {
        logger.trace("pausing for {}", nanoPause);
        long free = System.nanoTime() + nanoPause;
        while (System.nanoTime() < free) {}
        logger.trace("pause {} ended", nanoPause);
    }
}
