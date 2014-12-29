package mom.simulation;

import java.util.Optional;
import mom.event.Event;
import mom.event.EventSource;
import mom.publisher.Publisher;
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
