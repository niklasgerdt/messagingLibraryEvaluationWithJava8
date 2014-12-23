package eu.route20.hft.events;

import static org.junit.Assert.*;
import mom.event.Event;
import org.junit.*;
import eu.route20.hft.events.*;

public class MessageTest {

	@Test public void spike() {
		Event msg = new Event.Builder("", 1).build();
//		assertEquals("", msg.getMessage());
	}

	@Test public void createMessageBasedOnOldMessage() {
		Event msg1 = new Event.Builder("", 1).build();
		Event msg2 = new Event.Builder(msg1, 2).build();
//		asssertEquals(msg1.getMessage(), msg2.getMessage());
	}

}
