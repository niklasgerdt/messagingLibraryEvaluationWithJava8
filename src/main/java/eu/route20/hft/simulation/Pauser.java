package eu.route20.hft.simulation;

import lombok.*;

public class Pauser {
	private final long nanoPause;

	public Pauser(long nanoPause) {
		this.nanoPause = nanoPause;
	}

	// Busy wait for granularity
	public void pause() {
		val free = System.nanoTime() + nanoPause;
		while (System.nanoTime() < free) {
		}
	}

}
