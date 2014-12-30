package moma.config;

import java.io.FileInputStream;
import java.io.IOException;
import javax.xml.transform.stream.StreamSource;
import lombok.NonNull;
import moma.config.api.Simulation;
import moma.config.api.Simulation.Listeners.Listener;
import moma.config.api.Simulation.Simulators.Simulator;
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
        SimulationConfiguration config = configureSimulation(simulation);
        configureSimulators(simulation, config);
        configureListeners(simulation, config);
        return config;
    }

    private void configureListeners(Simulation simulation, SimulationConfiguration config) {
        for (Listener l : simulation.getListeners().getListener()) {
            ListenerConfiguration lc = configFactory.listenerConfiguration();
            lc.setId(l.getId().intValue());
            config.addListenerConfig(lc);
        }
    }

    private void configureSimulators(Simulation simulation, SimulationConfiguration config) {
        for (Simulator s : simulation.getSimulators().getSimulator()) {
            SimulatorConfiguration sc = configFactory.simulatorConfiguration();
            sc.setSimulatorId(s.getId().intValue());
            sc.setEventContentLength(s.getEventContentLength().intValue());
            sc.setPauseBetweenEvents(s.getPauseBetweenEvents().intValue());
            config.addSimulatorConfig(sc);
        }
    }

    private SimulationConfiguration configureSimulation(Simulation simulation) {
        SimulationConfiguration config = configFactory.simulationConfiguration();
        config.setProfile(simulation.getSystemUnderTest());
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
