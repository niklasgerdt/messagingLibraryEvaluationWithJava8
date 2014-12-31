package mom.simulation;

import java.util.Set;
import lombok.NonNull;
import mom.config.ActiveListenerConfiguration;
import mom.config.ActiveSimulatorConfiguration;
import mom.config.ListenerConfiguration;
import mom.config.SimulationConfiguration;
import mom.config.SimulatorConfiguration;
import mom.config.XmlApiReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SimulationConfigurer {
    private final static Logger logger = LoggerFactory.getLogger(SimulationConfigurer.class);
    private final XmlApiReader apiReader;
    private final ActiveSimulatorConfiguration activeSimulator;
    private final ActiveListenerConfiguration activeListener;
    private final SimulationFactory simulationFactory;

    @Autowired
    public SimulationConfigurer(final XmlApiReader xmlApiReader, final ActiveSimulatorConfiguration actSim,
            final ActiveListenerConfiguration actLis, final SimulationFactory simulationFactory) {
        this.apiReader = xmlApiReader;
        this.activeSimulator = actSim;
        this.activeListener = actLis;
        this.simulationFactory = simulationFactory;
    }

    public Simulation configure(@NonNull final String apiFileName) {
        logger.info("reading simulation configuration from file {}", apiFileName);
        final SimulationConfiguration conf = apiReader.readSimulationConfiguration(apiFileName);
        configureSimulators(conf);
        configureListeners(conf);
        logger.info("building simulation");
        return simulationFactory.simulation();
    }

    private void configureListeners(final SimulationConfiguration conf) {
        logger.info("configuring listeners {}", conf.getListeners());
        Set<Listener> listeners = simulationFactory.listeners();
        for (ListenerConfiguration l : conf.getListeners()) {
            activeListener.setId(l.getId());
            listeners.add(simulationFactory.newListener());
        }
        logger.info("builded listeners {}", listeners);
    }

    private void configureSimulators(final SimulationConfiguration conf) {
        logger.info("configuring simulators {}", conf.getSimulators());
        Set<Simulator> simulators = simulationFactory.simulators();
        for (SimulatorConfiguration s : conf.getSimulators()) {
            activeSimulator.setSimulatorId(s.getSimulatorId());
            activeSimulator.setEventContentLength(s.getEventContentLength());
            activeSimulator.setPauseBetweenEvents(s.getPauseBetweenEvents());
            simulators.add(simulationFactory.simulator());
        }
        logger.info("builded simulators {}", simulators);
    }
}
