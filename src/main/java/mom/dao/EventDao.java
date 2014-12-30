package mom.dao;

import java.util.Set;
import javax.sql.DataSource;
import mom.event.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Component;

@Component
public class EventDao {
    final static Logger logger = LoggerFactory.getLogger(EventDao.class);
    private final NamedParameterJdbcTemplate jdbc;

    @Autowired
    public EventDao(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }

    public void insertAll(Set<Event> events) {
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(events.toArray());
        logger.debug("inserting events (%f) to db", events.size());
        String sql = "insert into events set sender = :senderId, receiver = :receiverId, created = :nanoCreated, routed = :nanoReceived";
        jdbc.batchUpdate(sql, batch);
    }
}
