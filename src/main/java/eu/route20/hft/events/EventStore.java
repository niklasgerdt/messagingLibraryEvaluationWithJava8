package eu.route20.hft.events;

import java.io.*;
import java.util.*;

public class EventStore {
	private final String fileName;
	private File file;
	private FileWriter fw;

	public EventStore(String file) {
		this.fileName = file;
		createNewFile();
	}

	public void flush(List<Message> events) {
		events.stream()
				.forEach(e -> writeLine(e));
	}

	public void seal() {
		try {
			fw.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		file = null;
	}

	private void createNewFile() {
		try {
			file = new File(fileName);
			if (file.exists())
				file.delete();
			file.createNewFile();
			fw = new FileWriter(file);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void writeLine(Message e) {
		try {
			fw.write(e.toString());
			fw.write(System.lineSeparator());
			fw.flush();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
}
