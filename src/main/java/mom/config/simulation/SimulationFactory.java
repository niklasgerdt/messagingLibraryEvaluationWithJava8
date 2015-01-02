package mom.config.simulation;

import java.util.Set;
import mom.simulation.Listener;
import mom.simulation.Simulation;
import mom.simulation.Simulator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SimulationFactory {
    private final static Logger logger = LoggerFactory.getLogger(SimulationFactory.class);
    private final ApplicationContext ctx;

    @Autowired
    public SimulationFactory(final ApplicationContext ctx) {
        logger.info("wiring context");
        this.ctx = ctx;
    }

    public Simulation simulation() {
        logger.info("serving {}", Simulation.class);
        Simulation s = ctx.getBean(Simulation.class);
        return s;
    }

    public Simulator simulator() {
        logger.info("serving {}", Simulator.class);
        return ctx.getBean(Simulator.class);
    }

    public Listener listener() {
        logger.info("serving {}", Listener.class);
        return ctx.getBean(Listener.class);
    }

    @SuppressWarnings("unchecked")
    public Set<Simulator> simulators() {
        logger.info("serving simulators", Set.class);
        return (Set<Simulator>) ctx.getBean("simulators");
    }

    @SuppressWarnings("unchecked")
    public Set<Listener> listeners() {
        logger.info("serving listeners", Set.class);
        return (Set<Listener>) ctx.getBean("listeners");
    }
}
