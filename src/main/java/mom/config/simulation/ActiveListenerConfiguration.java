package mom.config.simulation;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("activeListenerConfiguration")
public class ActiveListenerConfiguration extends ListenerConfiguration {

}
