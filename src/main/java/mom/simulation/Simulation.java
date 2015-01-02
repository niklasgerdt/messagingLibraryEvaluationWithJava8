package mom.simulation;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.NonNull;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class Simulation {
    final static Logger logger = LoggerFactory.getLogger(Simulation.class);
    @Setter
    private Set<Simulator> simulators;
    @Setter
    private Set<Listener> listeners;
    private final EndSimulation ending;

    @Autowired
    public Simulation(@NonNull EndSimulation endSimulation) {
        this.ending = endSimulation;
    }

    public void run() {
        logger.info("starting simulation with simulators {} and listeners {}", simulators, listeners);
        ExecutorService spawner = Executors.newFixedThreadPool(listeners.size());
        listeners.stream().peek(s -> logger.info("starting listener {}", s)).forEach(s -> spawner.execute(s));
        ExecutorService simSpawner = Executors.newFixedThreadPool(simulators.size());
        simulators.stream().peek(s -> logger.info("starting simulator {}", s)).forEach(s -> simSpawner.execute(s));
        while (!ending.isListeningEnded()) {}
        logger.info("forked simulators and listeners");
    }
}
