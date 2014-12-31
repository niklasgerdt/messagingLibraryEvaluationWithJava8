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
    private final static Logger logger = LoggerFactory.getLogger(SimulationBeans.class);
    private Set<Simulator> simulators = new HashSet<>();
    private Set<Listener> listeners = new HashSet<>();

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
}
