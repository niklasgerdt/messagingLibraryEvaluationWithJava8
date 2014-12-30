package mom.config;

import lombok.NonNull;
import mom.simulation.Simulation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SimulationConfigurer {
    private final XmlApiReader apiReader;

    @Autowired
    public SimulationConfigurer(final XmlApiReader xmlApiReader) {
        this.apiReader = xmlApiReader;
    }

    public Simulation configure(@NonNull final String apiFileName) {
        final SimulationConfiguration conf = apiReader.readSimulationConfiguration(apiFileName);
        System.out.println(conf.getProfile());
        System.out.println(conf.getSimulators());
        System.out.println(conf.getListeners());
        return null;
    }
}
