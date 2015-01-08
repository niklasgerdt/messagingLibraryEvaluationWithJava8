package mom.event;

import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({ "zero", "jero", "jerotest", "default" })
public class InMemoryEventDao implements EventDao {
    final static Logger logger = LoggerFactory.getLogger(InMemoryEventDao.class);
    private Set<Event> events;

    @Override
    public void insertAll(Set<Event> events) {
        logger.info("caching events");
        this.events = events;
    }

    @Override
    @Cacheable("average")
    public Double getRoutingAverageTime(int from, int to) {
        return events.stream().limit(to).skip(from).mapToLong(e -> routingTime(e)).summaryStatistics().getAverage();
    }

    @Override
    public Double getRoutingStandardDeviation(int from, int to) {
        double average = getRoutingAverageTime(from, to);
        double d = events.stream().limit(to).skip(from).mapToDouble(e -> Math.pow(routingTime(e) - average, 2)).summaryStatistics().getSum();
        double deviation = Math.sqrt(d / (to - from));
        return deviation;
    }

    private long routingTime(Event e) {
        return e.getNanoReceived() - e.getNanoCreated();
    }

    @Override
    public Long getRoutingMinTime(int from, int to) {
        return events.stream().limit(to).skip(from).mapToLong(e -> routingTime(e)).summaryStatistics().getMin();
    }

    @Override
    public Long getRoutingMaxTime(int from, int to) {
        return events.stream().limit(to).skip(from).mapToLong(e -> routingTime(e)).summaryStatistics().getMax();
    }
}
