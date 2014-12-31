package mom.simulation;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Lazy
@Scope("singleton")
public class Simulation {
    final static Logger logger = LoggerFactory.getLogger(Simulation.class);
    private final Set<Simulator> simulators;
    private final Set<Listener> listeners;

    @Autowired
    public Simulation(@Qualifier("simulators") @NonNull Set<Simulator> simulators,
            @Qualifier("listeners") @NonNull Set<Listener> listeners) {
        logger.info("initialising simulation with simulators {} and listeners {}", simulators, listeners);
        this.simulators = simulators;
        this.listeners = listeners;
    }

    public void run() {
        logger.info("starting simulation with simulators {} and listeners {}", simulators, listeners);
        ExecutorService listenerSpawner = Executors.newFixedThreadPool(listeners.size());
        listeners.forEach(s -> listenerSpawner.execute(s));
        simulators.stream().parallel().forEach(s -> s.run());
        logger.info("forked simulators and listeners");
    }
}
