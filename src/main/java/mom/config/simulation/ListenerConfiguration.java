package mom.config.simulation;

import lombok.Data;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Data
@Qualifier("listenerConfiguration")
public class ListenerConfiguration {
    private int id;
}
