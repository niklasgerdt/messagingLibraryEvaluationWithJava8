package eu.route20.hft.simulation;

import static org.junit.Assert.*;
import lombok.*;
import org.junit.*;
import org.mockito.*;
import eu.route20.hft.events.*;
import eu.route20.hft.publisher.*;

public class SimulatorTest {
	@InjectMocks private Simulator simulator;
	@Mock private Publisher pub;
	@Mock private EventStore eventStore;

	@Before public void setup() {
		simulator = new Simulator(eventStore, pub);
		MockitoAnnotations.initMocks(this);
	}

	@Test public void sendsNotificationToPublisher() {
		simulator.setMsgLimit(10);
		simulator.run();
		Mockito.verify(pub, Mockito.atLeast(1))
				.pub(Mockito.any());
	}

	@Test public void sendsRightAmountOfNotifications() throws InterruptedException {
		simulator.setMsgLimit(10);
		simulator.run();
		Mockito.verify(pub, Mockito.times(10))
				.pub(Mockito.any());
	}

	@Ignore @Test public void pausesBetweenNotification() throws InterruptedException {
		int pauseInMillis = 100;
		simulator.setNanoPause(1);
		val t1 = System.currentTimeMillis();
		// simulator.doSimulation();
		val t2 = System.currentTimeMillis();
		assertEquals(2 * pauseInMillis, t2 - t1, 2 * (pauseInMillis / 10));
	}

	// @Test(expected = AssertionError.class)
	// public void randomMsgs() throws InterruptedException {
	// int amountOfnotifications = 1000;
	// notifications = new ArrayList<byte[]>();
	// //TODO Use Java 8 and lambdas
	// simulator.setPublisher(new Publisher() {
	// @Override
	// public void pub(byte[] notification) {
	// notifications.add(notification);
	// }
	// });
	// simulator.setNotificationLengthInBytes(amountOfnotifications);
	// simulator.setNotifications(2);
	// simulator.doSimulation();
	// int i = 0;
	// for (i = 0; i < amountOfnotifications; i++)
	// assertEquals(notifications.get(0)[i], notifications.get(1)[i]);
	// }
}
