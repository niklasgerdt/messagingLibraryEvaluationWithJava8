package moma.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SimulationConfigurationFactory {
    private final ApplicationContext ctx;

    @Autowired
    public SimulationConfigurationFactory(final ApplicationContext applicationContext) {
        this.ctx = applicationContext;
    }

    @Bean
    public SimulationConfiguration simulationConfiguration() {
        return ctx.getBean(SimulationConfiguration.class);
    }

    @Bean
    public SimulatorConfiguration simulatorConfiguration() {
        return ctx.getBean(SimulatorConfiguration.class);
    }

    @Bean
    public ListenerConfiguration listenerConfiguration() {
        return ctx.getBean(ListenerConfiguration.class);
    }
}
