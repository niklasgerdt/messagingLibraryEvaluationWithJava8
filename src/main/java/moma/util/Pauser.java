package moma.util;

import moma.config.SimulationConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Lazy
public class Pauser {
    private final int nanoPause;

    @Autowired
    public Pauser(SimulationConfiguration conf) {
        this.nanoPause = conf.getSimulators().get(0).getPauseBetweenEvents();
    }

    // Busy wait for granularity
    public void pause() {
        long free = System.nanoTime() + nanoPause;
        while (System.nanoTime() < free) {}
    }
}
