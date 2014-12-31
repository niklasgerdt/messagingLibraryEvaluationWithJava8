package mom.simulation;

import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

@Configuration
public class SimulationBeans {
    private final static Logger logger = LoggerFactory.getLogger(SimulationFactory.class);
    private Set<Simulator> simulators = new HashSet<>();
    private Set<Listener> listeners = new HashSet<>();

    @Bean
    @Lazy
    public Simulation simulation() {
        logger.info("serving {}", Simulation.class);
        // Simulation s = ctx.getBean(Simulation.class);
        Simulation s = new Simulation(simulators, listeners);
        logger.info("created {}", s);
        return s;
    }

    @Bean
    public Simulator simulator() {
        logger.info("serving {}", Simulator.class);
        return ctx.getBean(Simulator.class);
    }

    @Bean(name = "newListener")
    public Listener newListener() {
        logger.info("serving {}", Listener.class);
        return ctx.getBean(Listener.class);
    }

    @Bean(name = "simulators")
    @Scope("singleton")
    @Lazy
    public Set<Simulator> simulators() {
        logger.info("serving simulators", Set.class);
        return simulators;
    }

    @Bean(name = "listeners")
    @Scope("singleton")
    @Lazy
    public Set<Listener> listeners() {
        logger.info("serving listeners", Set.class);
        return listeners;
    }

    // @Bean
    // @Lazy
    // @Scope("prototype")
    // public Subscriber subscriber() {
    // logger.info("serving ", Subscriber.class);
    // return ctx.getBean(Subscriber.class);
    // }
}
