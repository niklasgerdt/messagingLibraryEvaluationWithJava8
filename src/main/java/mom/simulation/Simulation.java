package mom.simulation;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Simulation(Set<Simulator> simulators, Set<Listener> listeners) {
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
