package eu.route20.hft.simulation;

public class Kill {
	static private boolean killed = false;

	static void register() {
		Runtime.getRuntime()
				.addShutdownHook(new Thread() {
					@Override public void run() {
						Kill.kill(true);
					}
				});
	}

	static void kill(boolean kill) {
		System.out.println("SS");
		killed = kill;
	}

	static boolean killed() {
		return killed;
	}
}
