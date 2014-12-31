package mom;

import static org.junit.Assert.assertTrue;
import mom.config.Config;
import mom.simulation.Simulation;
import mom.simulation.SimulationConfigurer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class ConfigurationTest {
    @Autowired
    private ApplicationContext ctx;

    @Test
    public void runSimpleSimulations() {
        ((AbstractApplicationContext) ctx).registerShutdownHook();
        SimulationConfigurer config = ctx.getBean(SimulationConfigurer.class);
        Simulation simulation = config.configure("src/test/resources/testsimulation.xml");
        simulation.run();
        assertTrue(true);
    }
}
