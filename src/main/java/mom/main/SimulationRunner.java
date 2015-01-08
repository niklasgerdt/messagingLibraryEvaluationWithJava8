package mom.main;

import mom.analysis.Analysator;
import mom.config.SimulationBaseConfig;
import mom.config.simulation.SimulationConfigurer;
import mom.simulation.Simulation;
import mom.util.Logging;
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

        System.setProperty("spring.profiles.active", args[1]);
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SimulationBaseConfig.class);
        ((AbstractApplicationContext) ctx).registerShutdownHook();

        SimulationConfigurer config = ctx.getBean(SimulationConfigurer.class);
        Simulation simulation = config.configure(args[0]);
        simulation.run();

        Analysator analysator = ctx.getBean(Analysator.class);
        analysator.analyze();
        analysator.flush();

        logger.info("ending simulation");
        ((AbstractApplicationContext) ctx).close();
        Logging.stopLogging();
    }
}
