package moma.config;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SimulationConfigurer {
    private final XmlApiReader apiReader;

    @Autowired
    public SimulationConfigurer(final XmlApiReader xmlApiReader) {
        this.apiReader = xmlApiReader;
    }

    public moma.simulation.Simulation configure(@NonNull final String apiFileName) {
        final SimulationConfiguration conf = apiReader.readSimulationConfiguration(apiFileName);

        return null;
    }
}
