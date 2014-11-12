package eu.route20.hft.simulation.config;

import java.io.*;
import java.util.*;
import javax.xml.bind.*;
import eu.route20.hft.simulation.*;

public class SimulationConfiguration {

	public static List<Simulator> jeroMq(File file){
		SimulationConfig config;
		try {
			config = marshalConfig(file);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
		System.out.println(config);		
		return null;
	}

	private static SimulationConfig marshalConfig(File file) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(SimulationConfig.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		SimulationConfig config = (SimulationConfig) jaxbUnmarshaller.unmarshal(file);
		return config;
	}
}
