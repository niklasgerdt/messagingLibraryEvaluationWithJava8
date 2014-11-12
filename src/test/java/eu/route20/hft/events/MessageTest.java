package eu.route20.hft.events;

import static org.junit.Assert.*;
import org.junit.*;
import eu.route20.hft.events.*;

public class MessageTest {

	@Test public void spike() {
		Message msg = new Message.Builder("", 1).build();
		assertEquals("", msg.getMessage());
	}

	@Test public void createMessageBasedOnOldMessage() {
		Message msg1 = new Message.Builder("", 1).build();
		Message msg2 = new Message.Builder(msg1, 2).build();
		assertEquals(msg1.getMessage(), msg2.getMessage());
	}

}
