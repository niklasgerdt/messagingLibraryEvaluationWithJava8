package moma.main;

import mom.util.Logging;
import moma.config.Config;
import moma.config.SimulationConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class SimulationRunner {
    final static Logger logger = LoggerFactory.getLogger(SimulationRunner.class);

    public static void main(String[] args) {
        Logging.logbackStatus();
        logger.info("starting simulation");

        @SuppressWarnings("resource")
        ApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class);
        ((AbstractApplicationContext) ctx).registerShutdownHook();
        SimulationConfigurer config = ctx.getBean(SimulationConfigurer.class);
        System.out.println(args[0]);
        config.configure(args[0]);
        // Simulation simulation = ctx.getBean(Simulation.class);
        // simulation.run();

        logger.info("ending simulation");
        Logging.stopLogging();
    }
}
