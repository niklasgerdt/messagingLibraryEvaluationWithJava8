package mom.config;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Data
public class SimulatorConfiguration {
    private int eventContentLength;
    private int pauseBetweenEvents;
    private int simulatorId;
}
