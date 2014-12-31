package mom.simulation;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public void run() {
        logger.info("starting simulation with simulators {} and listeners {}", simulators, listeners);
        ExecutorService spawner = Executors.newFixedThreadPool(simulators.size());
        simulators.stream().peek(s -> logger.info("starting simulator {}", s)).forEach(s -> spawner.execute(s));
        listeners.stream().parallel().peek(s -> logger.info("starting listener {}", s)).forEach(s -> s.run());
        logger.info("forked simulators and listeners");
    }
}
