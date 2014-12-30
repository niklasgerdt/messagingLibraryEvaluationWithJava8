package mom.simulation;

import java.util.Set;
import lombok.NonNull;
import mom.config.ActiveListenerConfiguration;
import mom.config.ActiveSimulatorConfiguration;
import mom.config.ListenerConfiguration;
import mom.config.SimulationConfiguration;
import mom.config.SimulatorConfiguration;
import mom.config.XmlApiReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SimulationConfigurer {
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
        final SimulationConfiguration conf = apiReader.readSimulationConfiguration(apiFileName);
        configureSimulators(conf);
        configureListeners(conf);
        return simulationFactory.simulation();
    }

    private void configureListeners(final SimulationConfiguration conf) {
        Set<Listener> listeners = simulationFactory.listeners();
        for (ListenerConfiguration l : conf.getListeners()) {
            activeListener.setId(l.getId());
            listeners.add(simulationFactory.listener());
        }
    }

    private void configureSimulators(final SimulationConfiguration conf) {
        Set<Simulator> simulators = simulationFactory.simulators();
        for (SimulatorConfiguration s : conf.getSimulators()) {
            activeSimulator.setSimulatorId(s.getSimulatorId());
            activeSimulator.setEventContentLength(s.getEventContentLength());
            activeSimulator.setPauseBetweenEvents(s.getPauseBetweenEvents());
            simulators.add(simulationFactory.simulator());
        }
    }
}
