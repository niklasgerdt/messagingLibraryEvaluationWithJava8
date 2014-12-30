package moma.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import mom.config.SimulationConfiguration;
import mom.config.SimulationConfigurationFactory;
import mom.config.SimulatorConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class SimulationConfigurationFactoryTest {
    @Autowired
    private SimulationConfigurationFactory factory;

    @Test
    public void simulationConfigurationIsSingleton() {
        SimulationConfiguration c1 = factory.simulationConfiguration();
        SimulationConfiguration c2 = factory.simulationConfiguration();
        assertEquals(c1, c2);
    }

    @Test
    public void simulatorConfigurationIsPrototype() {
        SimulatorConfiguration c1 = factory.simulatorConfiguration();
        SimulatorConfiguration c2 = factory.simulatorConfiguration();
        c1.setSimulatorId(1);
        c2.setSimulatorId(2);
        assertNotEquals(c1.toString(), c2.toString());
    }

    @Configuration
    @ComponentScan(basePackages = "moma.config")
    static class Config {
    }
}
