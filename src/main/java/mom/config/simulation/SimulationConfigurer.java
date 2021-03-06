package mom.config.simulation;

import java.util.Set;
import lombok.NonNull;
import mom.config.api.XmlApiReader;
import mom.simulation.Listener;
import mom.simulation.Simulation;
import mom.simulation.Simulator;
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
        Set<Simulator> simulators = configureSimulators(conf);
        Set<Listener> listeners = configureListeners(conf);
        logger.info("building simulation");
        Simulation s = simulationFactory.simulation();
        s.setSimulators(simulators);
        s.setListeners(listeners);
        logger.info("builded simulation");
        return s;
    }

    private Set<Simulator> configureSimulators(final SimulationConfiguration conf) {
        logger.info("configuring simulators {}", conf.getSimulators());
        Set<Simulator> simulators = simulationFactory.simulators();
        for (SimulatorConfiguration s : conf.getSimulators()) {
            activeSimulator.setSimulatorId(s.getSimulatorId());
            activeSimulator.setEventContentLength(s.getEventContentLength());
            activeSimulator.setPauseBetweenEvents(s.getPauseBetweenEvents());
            simulators.add(simulationFactory.simulator());
        }
        logger.info("builded simulators {}", simulators);
        return simulators;
    }

    private Set<Listener> configureListeners(final SimulationConfiguration conf) {
        logger.info("configuring listeners {}", conf.getListeners());
        Set<Listener> listeners = simulationFactory.listeners();
        for (ListenerConfiguration l : conf.getListeners()) {
            activeListener.setId(l.getId());
            listeners.add(simulationFactory.listener());
        }
        logger.info("builded listeners {}", listeners);
        return listeners;
    }
}
