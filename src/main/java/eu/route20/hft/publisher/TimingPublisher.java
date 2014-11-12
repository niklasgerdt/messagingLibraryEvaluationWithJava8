package eu.route20.hft.publisher;

import lombok.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;
import route20.hft.annotations.*;

@Component @Profile("timing") @Qualifier("publisher") @Threadsafe public class TimingPublisher implements Publisher {
	final static Logger logger = LoggerFactory.getLogger(TimingPublisher.class);
	@Getter private long firstMsgTs;
	@Getter private long lastMsgTs;
	@Getter private long messages;

	@Override public void pub(String notification) {
		synchronized (notification) {
			logger.trace("Received notification, length {} bytes.", notification);
			lastMsgTs = System.currentTimeMillis();
			if (firstMsgTs == 0)
				firstMsgTs = lastMsgTs;
			messages++;
		}
	}
}
