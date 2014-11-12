package eu.route20.hft.simulation;

import java.util.*;
import java.util.stream.*;
import lombok.*;
import org.slf4j.*;
import eu.route20.hft.events.*;
import eu.route20.hft.publisher.*;

public class Simulator implements Runnable {
	final static Logger logger = LoggerFactory.getLogger(Simulator.class);
	private Publisher publisher;
	private EventStore eventStore;
	@Setter private int msgLen = 100;
	@Setter private long nanoPause;
	@Setter private int id;
	private String message;
	@Setter private long msgLimit = Long.MAX_VALUE;

	public Simulator(EventStore eventStore, Publisher publisher) {
		this.eventStore = eventStore;
		this.publisher = publisher;
	}

	public Simulator() {
		Random r = new Random();
		val bytes = new byte[msgLen];
		r.nextBytes(bytes);
		message = bytes.toString();
	}

	@Override public void run() {
		logger.debug("Simulating notifications of {} bytes with pause {}.", msgLen, nanoPause);
		List<Optional<Message>> optionalEvents = Stream.generate(() -> createMessage())
				.peek(x -> pause())
				.filter(msg -> msg.isPresent())
				.peek(msg -> publisher.pub(msg.toString()))
				.limit(msgLimit)
				.collect(Collectors.toList());
		List<Message> events = optionalEvents.stream()
				.map(x -> x.get())
				.collect(Collectors.toList());
		eventStore.flush(events);
	}

	private Optional<Message> createMessage() {
		Message msg = new Message.Builder(message, id).build();
		if (Kill.killed())
			return Optional.empty();
		else
			return Optional.of(msg);
	}

	// Busy wait for granularity
	private void pause() {
		val free = System.nanoTime() + nanoPause;
		while (System.nanoTime() < free) {
		}
	}

}
