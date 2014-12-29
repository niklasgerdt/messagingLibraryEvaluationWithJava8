package mom.system;

import mom.simulation.Simulation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/ApplicationContext.xml")
public class SimulationIntegrationTest {
    @Autowired
    private Simulation simulation;

    @Test
    public void runsClean() {
        simulation.run();
    }
}
