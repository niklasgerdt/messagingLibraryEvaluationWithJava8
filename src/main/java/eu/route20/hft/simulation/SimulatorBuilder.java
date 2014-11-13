package eu.route20.hft.simulation;

import java.util.function.*;
import lombok.*;
import eu.route20.hft.events.*;
import eu.route20.hft.publisher.*;
import eu.route20.hft.simulation.Simulator.*;

public class SimulatorBuilder {
	private Consumer<String> publisher;
	private EventStore eventStore;
	@Setter private int msgLen = 100;
	@Setter private long nanoPause;
	@Setter private int id;
	@Setter private long msgLimit = Long.MAX_VALUE;
	final private VoidConsumer pauser = () -> {
	};
	
	public SimulatorBuilder(int id, EventStore eventStore, Publisher publisher) {

	}

}
