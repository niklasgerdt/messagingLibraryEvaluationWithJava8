package mom.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("activeSimulatorConfiguration")
public class ActiveSimulatorConfiguration extends SimulatorConfiguration {
}
