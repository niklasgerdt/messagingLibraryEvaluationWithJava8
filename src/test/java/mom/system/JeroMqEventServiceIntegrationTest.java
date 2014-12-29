package mom.system;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import mom.eventservice.EventService;
import mom.util.KillSwitch;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JeroMqEventServiceIntegrationTest {

    @Test
    public void test() throws InterruptedException {
        @SuppressWarnings("resource")
        ApplicationContext ctx = new ClassPathXmlApplicationContext("EventServiceContext.xml");
        EventService es = ctx.getBean(EventService.class);
        KillSwitch killSwitch = ctx.getBean(KillSwitch.class);
        ExecutorService exec = Executors.newSingleThreadExecutor();
        exec.submit(es);
        Thread.sleep(100); 
        killSwitch.setAndNotify(true);
    }
}
