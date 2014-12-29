package moma.config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import moma.publisher.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Profile({ "zero", "basic" })
@PropertySource("classpath:/basic_simulation.properties")
public class PublisherFactory {
    @Autowired
    private Publisher pub1;
    @Autowired
    private Publisher pub2;
    @Autowired
    private Publisher pub3;
    @Autowired
    private Publisher pub4;

    @Bean
    public Set<Publisher> publishers() {
        pub1.bind("tcp://169.1.1.2:5001");
        pub2.bind("tcp://169.1.1.2:5002");
        pub3.bind("tcp://169.1.1.2:5003");
        pub4.bind("tcp://169.1.1.2:5004");
        List<Publisher> publist = Arrays.asList(pub1, pub2, pub3, pub4);
        Set<Publisher> publishers = new HashSet<Publisher>(publist);
        return publishers;
    }
}
