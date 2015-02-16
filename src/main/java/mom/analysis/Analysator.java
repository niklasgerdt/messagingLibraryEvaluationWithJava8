package mom.analysis;

import mom.event.EventDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@Profile({ "zero", "jero", "jerotest", "default" })
@PropertySource("classpath:/basic_simulation.properties")
public class Analysator {
    private final EventDao eventDao;
    private final Report report;
    private final ReportWriter writer;
    @Value("${analyseFrom}")
    private int from;
    @Value("${analyseTo}")
    private int to;

    @Autowired
    public Analysator(final EventDao eventDao, final Report report, final ReportWriter writer) {
        this.eventDao = eventDao;
        this.report = report;
        this.writer = writer;
    }

    @Cacheable("report")
    public Report analyze() {
        eventDao.commonStatistics(from, to);
        report.setAverageTime(eventDao.getRoutingAverageTime(from, to));
        report.setStandardDeviation(eventDao.getRoutingStandardDeviation(from, to));
        report.setMin(eventDao.getRoutingMinTime(from, to));
        report.setMax(eventDao.getRoutingMaxTime(from, to));
        return report;
    }

    public void flush() {
        report.log();
        writer.write(report);
    }
}
