package moma.simulation;

import java.util.Optional;
import moma.event.Event;
import moma.event.EventSource;
import moma.publisher.Publisher;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class SimulatorTest {
    @Mock
    private EventSource src;
    @Mock
    private Publisher pub;
    @Mock
    private Event e;
    private int limit = 3;
    private Simulator sim;

    @Before
    public void setupMocks() {
        MockitoAnnotations.initMocks(this);
        sim = new Simulator(src, pub, limit);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void simulatorLimitsEventsPassed() {
        Mockito.when(src.get()).thenReturn(Optional.of(e), Optional.of(e), Optional.of(e), Optional.of(e));
        sim.run();
        Mockito.verify(pub, Mockito.times(limit)).pub(e);
    }
}
