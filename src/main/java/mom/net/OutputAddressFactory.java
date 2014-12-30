package mom.net;

import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:/basic_simulation.properties")
public class OutputAddressFactory {
    private final AtomicInteger port;
    private final String address;

    public OutputAddressFactory(@Value("${baseOutputPort}") final int basePort,
            @Value("${baseOutputAddress}") final String address) {
        this.port = new AtomicInteger(basePort);
        this.address = address;
    }

    public String getNextAddress() {
        final int currentPort = port.getAndIncrement();
        return address + currentPort;
    }
}
