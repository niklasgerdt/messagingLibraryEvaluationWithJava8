package moma.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:/basic_simulation.properties")
public class Pauser {
    @Value("${pauselength}")
    private String nanoPauseStr;

    // Busy wait for granularity
    public void pause() {
        int nanoPause = Integer.parseInt(nanoPauseStr);
        long free = System.nanoTime() + nanoPause;
        while (System.nanoTime() < free) {}
    }
}
