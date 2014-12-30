package moma.config;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:/basic_simulation.properties")
public class SimulationConfiguration {
    @Getter
    @Setter
    private String profile;
    @Getter
    private List<SimulatorConfiguration> simulators = new ArrayList<>();
    @Getter
    private List<ListenerConfiguration> listeners = new ArrayList<>();

    public void addSimulatorConfig(SimulatorConfiguration simConf) {
        simulators.add(simConf);
    }

    public void addListenerConfig(ListenerConfiguration listConf) {
        listeners.add(listConf);
    }
}
