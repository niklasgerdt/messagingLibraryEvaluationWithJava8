package moma.config;

import java.io.FileInputStream;
import java.io.IOException;
import javax.xml.transform.stream.StreamSource;
import lombok.NonNull;
import moma.config.api.Simulation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.Unmarshaller;
import org.springframework.stereotype.Component;

@Component
public class XmlApiReader {
    private final Unmarshaller unmarshaller;
    private final SimulationConfigurationFactory configFactory;

    @Autowired
    public XmlApiReader(final Unmarshaller unmarshaller,
            final SimulationConfigurationFactory simulationConfigurationFactory) {
        this.unmarshaller = unmarshaller;
        this.configFactory = simulationConfigurationFactory;
    }

    public SimulationConfiguration readSimulationConfiguration(@NonNull final String apiFileName) {
        Simulation simulation = readSimulationConfigurationFile(apiFileName);
        SimulationConfiguration config = configFactory.simulationConfiguration();
        config.setProfile(simulation.getSystemUnderTest());
        SimulatorConfiguration simConf = configFactory.simulatorConfiguration();
        simulation.getSimulators().getSimulator();
        simConf.setSimulatorId(simulation.getSimulators().getSimulator().get(0).getId().intValue());
        config.addSimulatorConfig(simConf);
        return config;
    }

    private Simulation readSimulationConfigurationFile(String apiFileName) {
        FileInputStream is = null;
        try {
            is = new FileInputStream(apiFileName);
            return (Simulation) unmarshaller.unmarshal(new StreamSource(is));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            closeFile(is);
        }
    }

    private void closeFile(FileInputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
