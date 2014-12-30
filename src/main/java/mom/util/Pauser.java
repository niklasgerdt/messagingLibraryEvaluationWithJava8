package mom.util;

import mom.config.ActiveSimulatorConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Lazy
public class Pauser {
    private final int nanoPause;

    @Autowired
    public Pauser(@Qualifier("activeSimulatorConfiguration") ActiveSimulatorConfiguration conf) {
        this.nanoPause = conf.getPauseBetweenEvents();
    }

    // Busy wait for granularity
    public void pause() {
        long free = System.nanoTime() + nanoPause;
        while (System.nanoTime() < free) {}
    }
}
