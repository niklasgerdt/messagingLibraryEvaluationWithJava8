package mom.event;

import java.util.Set;

public interface EventDao {

    void insertAll(Set<Event> events);

    Double getRoutingAverageTime(int from, int to);

    Double getRoutingStandardDeviation(int from, int to);

    Long getRoutingMinTime(int from, int to);

    Long getRoutingMaxTime(int from, int to);

}