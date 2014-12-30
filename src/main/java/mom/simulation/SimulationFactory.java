package mom.simulation;

import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SimulationFactory {
    private final ApplicationContext ctx;

    @Autowired
    public SimulationFactory(final ApplicationContext ctx) {
        this.ctx = ctx;
    }

    @Bean
    public Simulation simulation() {
        return ctx.getBean(Simulation.class);
    }

    @Bean
    public Simulator simulator() {
        return ctx.getBean(Simulator.class);
    }

    @Bean
    public Listener listener() {
        return ctx.getBean(Listener.class);
    }

    @Bean
    public Set<Simulator> simulators() {
        return new HashSet<Simulator>();
    }

    @Bean
    public Set<Listener> listeners() {
        return new HashSet<Listener>();
    }
}
