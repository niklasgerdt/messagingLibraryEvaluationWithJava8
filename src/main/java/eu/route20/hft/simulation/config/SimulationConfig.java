package eu.route20.hft.simulation.config;

import javax.xml.bind.annotation.*;

@XmlRootElement
public class SimulationConfig {
	private String publisher;
	private String address;
	private long pause;
	private int messageLength;
	
	
}
