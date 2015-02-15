package mom.event;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({ "zero", "jero", "jerotest", "default" })
public class InMemoryEventDao implements EventDao {
    private static final int SPARE_OVER_LIMIT = 1_000_000;
    final static Logger logger = LoggerFactory.getLogger(InMemoryEventDao.class);
    private List<Event> events;
    @Value("${eventlimit}")
    private int limit;

    @Override
    public void insertAll(Set<Event> events) {
        logger.info("caching events");
        if ((events.size() < limit - SPARE_OVER_LIMIT))
            throw new RuntimeException("not enough events for analysis");
        this.events = events.stream().sorted((a, b) -> Integer.valueOf(a.getId()).compareTo(Integer.valueOf(b.getId()))).collect(Collectors.toList());
    }

    @Override
    public void commonStatistics(int from, int to) {
        events.stream().mapToLong(e -> e.getNanoReceived()).sorted().skip(from - 1).limit(1).forEach(e -> logger.info("ev num {} is {}", from, e));
        events.stream().mapToLong(e -> e.getNanoReceived()).sorted().skip(to - 1).limit(1).forEach(e -> logger.info("ev num {} is {}", to, e));
        Map<Integer, List<Event>> grouped = events.stream().sorted((a, b) -> Long.valueOf(a.getNanoReceived()).compareTo(Long.valueOf(b.getNanoReceived()))).limit(to).skip(from).collect(Collectors.groupingBy(e -> e.getSenderId()));
        grouped.keySet().forEach(k -> grouped.get(k).stream().findFirst().ifPresent(e -> logger.info("event {}:{}", e.getSenderId(), e.getId())));
        grouped.keySet().forEach(k ->
        {
            List<Event> es = grouped.get(k);
            Collections.reverse(es);
            es.stream().findFirst().ifPresent(e -> logger.info("event {}:{}", e.getSenderId(), e.getId()));
        });
    }

    @Override
    public void listenerStatistics(int from, int to) {
        Map<Integer, List<Event>> grouped = events.stream().sorted((a, b) -> Long.valueOf(a.getNanoReceived()).compareTo(Long.valueOf(b.getNanoReceived()))).limit(to).skip(from).collect(Collectors.groupingBy(e -> e.getReceiverId()));
        grouped.keySet().forEach(k -> grouped.get(k).stream().findFirst().ifPresent(e -> logger.info("first event {}:{}", e.getReceiverId(), e.getId())));
        grouped.keySet().forEach(k ->
        {
            List<Event> es = grouped.get(k);
            Collections.reverse(es);
            es.stream().findFirst().ifPresent(e -> logger.info("last event {}:{}", e.getReceiverId(), e.getId()));
        });
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
