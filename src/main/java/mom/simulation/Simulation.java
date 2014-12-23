package mom.simulation;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Simulation {
    final static Logger logger = LoggerFactory.getLogger(Simulation.class);
    private final Set<Simulator> simulators;
    private final Set<Listener> listeners;

    public Simulation(Set<Simulator> simulators, Set<Listener> listeners) {
        this.simulators = simulators;
        this.listeners = listeners;
    }

    public void run() {
        logger.info("starting simulation with simulators (%s) and listeners (%s)", simulators, listeners);
        ExecutorService listenerSpawner = Executors.newFixedThreadPool(listeners.size());
        listeners.forEach(s -> listenerSpawner.execute(s));
        ExecutorService simulatorSpawner = Executors.newFixedThreadPool(simulators.size());
        simulators.forEach(s -> simulatorSpawner.execute(s));
        logger.info("forked simulators and listeners");
    }
}
