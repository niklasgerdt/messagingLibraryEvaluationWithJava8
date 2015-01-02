package mom.eventservice.jeromq;

import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("jero")
public class JeroInAddresses {
    @Getter
    private List<String> inAddresses = Arrays.asList("tcp://localhost:5000", "tcp://localhost:5001", "tcp://localhost:5002", "tcp://localhost:5003");
}
