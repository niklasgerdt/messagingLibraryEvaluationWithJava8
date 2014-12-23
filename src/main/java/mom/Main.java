package mom;

import mom.simulation.Simulation;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) {
        @SuppressWarnings("resource")
        ApplicationContext ctx = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        ((AbstractApplicationContext) ctx).registerShutdownHook();

        Simulation simulation = ctx.getBean(Simulation.class);
        simulation.run();
    }
}
