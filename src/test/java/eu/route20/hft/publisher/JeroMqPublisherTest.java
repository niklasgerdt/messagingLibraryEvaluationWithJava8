package eu.route20.hft.publisher;

import org.junit.*;
import org.junit.runner.*;
import org.mockito.*;
import org.mockito.Mock;
import org.powermock.api.mockito.*;
import org.powermock.core.classloader.annotations.*;
import org.powermock.modules.junit4.*;
import org.zeromq.*;

@RunWith(PowerMockRunner.class) @PrepareForTest({ ZMQ.class, ZMQ.Socket.class }) public class JeroMqPublisherTest {
	@Mock private ZMQ.Context context;
	@InjectMocks private JeroMqPublisher pub;
	private ZMQ.Socket socket = PowerMockito.mock(ZMQ.Socket.class);

	@Before public void initMocks() {
		pub = new JeroMqPublisher();
		pub.setAddress("TCP");
		MockitoAnnotations.initMocks(this);
		PowerMockito.mockStatic(ZMQ.class);
		Mockito.when(ZMQ.context(1))
				.thenReturn(context);
		Mockito.when(context.socket(ZMQ.PUB))
				.thenReturn(socket);
	}

	@Test public void setupJeroMqConnection() {
		pub.up();
		PowerMockito.verifyStatic();
		ZMQ.context(1);
		Mockito.verify(context)
				.socket(ZMQ.PUB);
		Mockito.verify(socket)
				.connect("TCP");
	}

	@Test public void sendMsg() {
		pub.up();
		String notification = "msg";
		pub.pub(notification);
		Mockito.verify(socket)
				.send(notification);
	}

	@Test public void tearDown() {
		pub.up();
		pub.down();
		Mockito.verify(socket)
				.close();
		Mockito.verify(context)
				.term();
	}
}
