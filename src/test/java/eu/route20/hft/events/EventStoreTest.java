package eu.route20.hft.events;

import static org.junit.Assert.*;
import java.io.*;
import java.util.*;
import org.junit.*;

public class EventStoreTest {
	private static final String FILE = "FILENAMEFORTESTS";
	private EventStore es = new EventStore(FILE);

	@Test public void test() {
		List<Message> events = new ArrayList<>();
		events.add(new Message.Builder("", 0).build());
		events.add(new Message.Builder("", 1).build());
		es.flush(events);
		File f = getFile();
		assertTrue(f.exists());
		assertEquals(168, f.length());
	}

	private File getFile() {
		File f = new File(FILE);
		return f;
	}

	@After public void tear() {
		getFile().delete();
	}

}
