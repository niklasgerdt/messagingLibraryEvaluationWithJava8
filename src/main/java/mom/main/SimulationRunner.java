package mom.main;

import mom.simulation.Simulation;
import mom.util.Logging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SimulationRunner {
    final static Logger logger = LoggerFactory.getLogger(SimulationRunner.class);

    public static void main(String[] args) {
        Logging.logbackStatus();
        logger.info("starting simulation");
        @SuppressWarnings("resource")
        ApplicationContext ctx = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        ((AbstractApplicationContext) ctx).registerShutdownHook();

        Simulation simulation = ctx.getBean(Simulation.class);
        simulation.run();

        logger.info("ending simulation");
        Logging.stopLogging();
    }
}
