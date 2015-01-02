package mom.simulation;

import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Component;

@Component
public class EndSimulation {
    private AtomicInteger activeSimulators;
    private AtomicInteger activeListeners;

    public EndSimulation() {
        activeSimulators = new AtomicInteger(0);
        activeListeners = new AtomicInteger(0);
    }

    public void activateSimulator() {
        activeSimulators.incrementAndGet();
    }

    public void deActivateSimulator() {
        activeSimulators.decrementAndGet();
    }

    public void activateListener() {
        activeListeners.incrementAndGet();
    }

    public void deActivateListener() {
        activeListeners.decrementAndGet();
    }

    public boolean isSimulationsEnded() {
        if (activeSimulators != null && activeSimulators.get() == 0)
            return true;
        return false;
    }

    public boolean isListeningEnded() {
        if (activeListeners != null && activeListeners.get() == 0)
            return true;
        return false;
    }
}
