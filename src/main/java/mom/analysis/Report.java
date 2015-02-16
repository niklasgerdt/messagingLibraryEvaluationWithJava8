package mom.analysis;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Data
public class Report {
    final static Logger logger = LoggerFactory.getLogger(Report.class);
    private double averageTime;
    private double standardDeviation;
    private long min;
    private long max;
    private int droppedEvents;

    public void log() {
        logger.info("simulations routing average time was {}", averageTime);
        logger.info("simulations routing standard deviation was {}", standardDeviation);
        logger.info("simulations routing min time was {}", min);
        logger.info("simulations routing max time was {}", max);
        logger.info("events lost during simulation {}", droppedEvents);
    }
}
