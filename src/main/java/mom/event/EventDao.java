package mom.event;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

@Component
public class EventDao {
    private static final int BATCH = 1000;
    final static Logger logger = LoggerFactory.getLogger(EventDao.class);
    private final NamedParameterJdbcTemplate jdbc;

    @Autowired
    public EventDao(DataSource dataSource) {
        logger.info("creating eventdao");
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }

    @PostConstruct
    public void clearEventTable() {
        String sql = "delete from events";
        jdbc.getJdbcOperations().execute(sql);
        logger.debug("deleted all from table events", sql);
    }

    public void insertAll(Set<Event> events) {
        String sql = "insert into events set id = ?, sender = ?, receiver = ?, created = ?, routed = ?";
        jdbc.getJdbcOperations().batchUpdate(sql, events, BATCH, new ParameterizedPreparedStatementSetter<Event>() {
            @Override
            public void setValues(PreparedStatement ps, Event e) throws SQLException {
                ps.setInt(1, e.getId());
                ps.setInt(2, e.getSenderId());
                ps.setInt(3, e.getReceiverId());
                ps.setLong(4, e.getNanoCreated());
                ps.setLong(5, e.getNanoReceived());
            }
        });
    }

    @PreDestroy
    public void results() {
        logger.info("fishing results");
        getRoutingAverageTime(10, 50);
        getRoutingStandardDeviation(10, 50);
        getRoutingMinTime(10, 50);
        getRoutingMaxTime(10, 50);
    }

    public Double getRoutingAverageTime(final int from, final int to) {
        SqlParameterSource params = new MapSqlParameterSource().addValue("from", from).addValue("to", to);
        String sql = "select avg(routed-created) from events where id > :from and id <= :to";
        Double avg = jdbc.queryForObject(sql, params, Double.class);
        logger.info("events average time is {}", avg);
        return avg;
    }

    public Double getRoutingStandardDeviation(final int from, final int to) {
        SqlParameterSource params = new MapSqlParameterSource().addValue("from", from).addValue("to", to);
        String sql = "select stddev_samp(routed-created) from events where id > :from and id <= :to";
        Double dev = jdbc.queryForObject(sql, params, Double.class);
        logger.info("events standard deviation is {}", dev);
        return dev;
    }

    public Long getRoutingMinTime(final int from, final int to) {
        SqlParameterSource params = new MapSqlParameterSource().addValue("from", from).addValue("to", to);
        String sql = "select min(routed-created) from events where id > :from and id <= :to";
        Long min = jdbc.queryForObject(sql, params, Long.class);
        logger.info("min routing time is {}", min);
        return min;
    }

    public Long getRoutingMaxTime(final int from, final int to) {
        SqlParameterSource params = new MapSqlParameterSource().addValue("from", from).addValue("to", to);
        String sql = "select max(routed-created) from events where id > :from and id <= :to";
        Long max = jdbc.queryForObject(sql, params, Long.class);
        logger.info("max routing time is {}", max);
        return max;
    }
}
