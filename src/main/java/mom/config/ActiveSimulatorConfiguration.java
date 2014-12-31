package mom.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Data
@Qualifier("activeSimulatorConfiguration")
public class ActiveSimulatorConfiguration {
    private int eventContentLength;
    private int pauseBetweenEvents;
    private int simulatorId;
}
