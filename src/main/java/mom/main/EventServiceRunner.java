package mom.main;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import mom.eventservice.EventService;
import mom.util.KillSwitch;
import mom.util.Logging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class EventServiceRunner {
    private static final String EVENT_SERVICE_CONTEXT_XML = "EventServiceContext.xml";
    final static Logger logger = LoggerFactory.getLogger(EventServiceRunner.class);

    public static void main(String[] args) {
        startLog();
        ApplicationContext ctx = handleContext();
        EventService es = ctx.getBean(EventService.class);
        KillSwitch killSwitch = ctx.getBean(KillSwitch.class);
        runApp(es, killSwitch);
        endLog();
    }

    private static ApplicationContext handleContext() {
        // System.setProperty("spring.profiles.active", "");
        ApplicationContext ctx = new ClassPathXmlApplicationContext(EVENT_SERVICE_CONTEXT_XML);
        ((AbstractApplicationContext) ctx).registerShutdownHook();
        return ctx;
    }

    private static void runApp(EventService es, KillSwitch killSwitch) {
        ExecutorService exec = Executors.newSingleThreadExecutor();
        exec.submit(killSwitch);
        es.run();
    }

    private static void endLog() {
        logger.info("stopping event service");
        Logging.stopLogging();
    }

    private static void startLog() {
        Logging.logbackStatus();
        logger.info("starting event service");
    }
}
