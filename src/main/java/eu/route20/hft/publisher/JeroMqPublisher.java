package eu.route20.hft.publisher;

import javax.annotation.*;
import lombok.*;
import org.zeromq.*;

public class JeroMqPublisher implements Publisher {
	private static final int ZMQ_THREADS = 1;
	private ZMQ.Context ctx;
	private ZMQ.Socket socket;
	@Setter private String address;

	@PostConstruct public void up() {
		ctx = ZMQ.context(ZMQ_THREADS);
		socket = ctx.socket(ZMQ.PUB);
		socket.connect(address);
	}

	@Override public void pub(String notification) {
		socket.send(notification);
	}

	@PreDestroy public void down() {
		socket.close();
		ctx.term();
	}
}
