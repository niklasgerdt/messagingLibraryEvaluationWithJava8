package mom.config.simulation;

import lombok.Data;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Data
@Qualifier("simulatorConfiguration")
public class SimulatorConfiguration {
    private int eventContentLength;
    private int pauseBetweenEvents;
    private int simulatorId;
}
